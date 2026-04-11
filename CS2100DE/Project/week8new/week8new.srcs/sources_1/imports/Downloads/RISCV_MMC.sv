`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: National University of Singapore
// Engineer: Neil Banerjee
//
// Create Date: 22.02.2025 21:29:09
// Design Name: RISCV-MMC
// Module Name: RISCV_MMC
// Project Name: CS2100DE Labs
// Target Devices: Nexys 4/Nexys 4 DDR
// Tool Versions: Vivado 2023.2
// Description: 5-stage pipelined RISC-V CPU (IF/ID/EX/MEM/WB)
//
// Dependencies: Nil
//
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
//
//////////////////////////////////////////////////////////////////////////////////


module RISCV_MMC(
    input clk,
    input rst,
    input [31:0] instr,
    input [31:0] mem_read_data,
    output mem_read,
    output mem_write,
    output [31:0] PC,
    output [31:0] alu_result,
    output [31:0] mem_write_data
    );

    // =========================================================================
    // Output assignments — memory interface driven from MEM stage (EX/MEM reg)
    // =========================================================================
    assign PC             = pc_current;
    assign alu_result     = exmem_alu_result;
    assign mem_write      = exmem_mem_write;
    assign mem_read       = exmem_mem_to_reg;
    assign mem_write_data = exmem_RD2;

    // =========================================================================
    // Hazard / control
    // =========================================================================
    wire stall;  // load-use hazard
    wire flush;  // branch/jump taken

    // Load-use: stall when EX has a load whose rd matches an ID source register
    assign stall = idex_mem_to_reg && (idex_rd != 5'b0) &&
                   ((idex_rd == ifid_instr[19:15]) || (idex_rd == ifid_instr[24:20]));

    // Flush IF/ID and ID/EX when a branch is taken or a jump executes in EX
    assign flush = (ex_PC_src != 2'b00);

    // =========================================================================
    // IF Stage
    // =========================================================================
    wire [31:0] pc_current, pc_next;

    assign pc_next = flush ? ex_pc_next : (pc_current + 4);

    ProgramCounter counter_uut (
        .clk(clk),
        .rst(rst),
        .pc_enable(~stall),
        .pc_in(pc_next),
        .pc(pc_current)
    );

    // =========================================================================
    // IF/ID Pipeline Register
    // =========================================================================
    reg [31:0] ifid_pc;
    reg [31:0] ifid_instr;

    always @(posedge clk) begin
        if (rst || flush) begin
            ifid_pc    <= 32'b0;
            ifid_instr <= 32'b0;
        end
        else if (!stall) begin
            ifid_pc    <= pc_current;
            ifid_instr <= instr;
        end
    end

    // =========================================================================
    // ID Stage
    // =========================================================================
    wire [31:0] id_ext_imm, id_RD1, id_RD2;
    wire [3:0]  id_alu_control;
    wire [2:0]  id_imm_src;
    wire [1:0]  id_PCS, id_alu_src_a;
    wire        id_mem_to_reg, id_alu_src_b, id_reg_write, id_pc_to_reg, id_mem_write;

    Decoder decoder_uut (
        .instr(ifid_instr),
        .PCS(id_PCS),
        .imm_src(id_imm_src),
        .mem_to_reg(id_mem_to_reg),
        .mem_write(id_mem_write),
        .alu_control(id_alu_control),
        .alu_src_a(id_alu_src_a),
        .alu_src_b(id_alu_src_b),
        .reg_write(id_reg_write),
        .pc_to_reg(id_pc_to_reg)
    );

    Extend extender_uut (
        .instr_imm(ifid_instr[31:7]),
        .imm_src(id_imm_src),
        .ext_imm(id_ext_imm)
    );

    // Register file — writes from WB stage, reads for ID stage
    RegFile reg_uut (
        .clk(clk),
        .we(memwb_reg_write),
        .rs1(ifid_instr[19:15]),
        .rs2(ifid_instr[24:20]),
        .rd(memwb_rd),
        .WD(wb_write_data),
        .RD1(id_RD1),
        .RD2(id_RD2)
    );

    // =========================================================================
    // ID/EX Pipeline Register
    // =========================================================================
    reg [31:0] idex_pc, idex_RD1, idex_RD2, idex_ext_imm;
    reg [4:0]  idex_rs1, idex_rs2, idex_rd;
    reg [3:0]  idex_alu_control;
    reg [2:0]  idex_funct3;
    reg [1:0]  idex_PCS, idex_alu_src_a;
    reg        idex_mem_to_reg, idex_alu_src_b, idex_reg_write, idex_pc_to_reg, idex_mem_write;

    always @(posedge clk) begin
        if (rst || flush || stall) begin
            idex_pc          <= 32'b0;
            idex_RD1         <= 32'b0;
            idex_RD2         <= 32'b0;
            idex_ext_imm     <= 32'b0;
            idex_rs1         <= 5'b0;
            idex_rs2         <= 5'b0;
            idex_rd          <= 5'b0;
            idex_alu_control <= 4'b0;
            idex_funct3      <= 3'b0;
            idex_PCS         <= 2'b0;
            idex_alu_src_a   <= 2'b0;
            idex_mem_to_reg  <= 1'b0;
            idex_alu_src_b   <= 1'b0;
            idex_reg_write   <= 1'b0;
            idex_pc_to_reg   <= 1'b0;
            idex_mem_write   <= 1'b0;
        end
        else begin
            idex_pc          <= ifid_pc;
            idex_RD1         <= id_RD1;
            idex_RD2         <= id_RD2;
            idex_ext_imm     <= id_ext_imm;
            idex_rs1         <= ifid_instr[19:15];
            idex_rs2         <= ifid_instr[24:20];
            idex_rd          <= ifid_instr[11:7];
            idex_alu_control <= id_alu_control;
            idex_funct3      <= ifid_instr[14:12];
            idex_PCS         <= id_PCS;
            idex_alu_src_a   <= id_alu_src_a;
            idex_mem_to_reg  <= id_mem_to_reg;
            idex_alu_src_b   <= id_alu_src_b;
            idex_reg_write   <= id_reg_write;
            idex_pc_to_reg   <= id_pc_to_reg;
            idex_mem_write   <= id_mem_write;
        end
    end

    // =========================================================================
    // EX Stage — Forwarding Unit
    // =========================================================================
    wire [1:0] forward_a, forward_b;

    // 2'b10 = forward from EX/MEM, 2'b01 = forward from MEM/WB, 2'b00 = no forward
    assign forward_a = (exmem_reg_write && exmem_rd != 5'b0 && exmem_rd == idex_rs1) ? 2'b10 :
                       (memwb_reg_write && memwb_rd != 5'b0 && memwb_rd == idex_rs1) ? 2'b01 :
                       2'b00;

    assign forward_b = (exmem_reg_write && exmem_rd != 5'b0 && exmem_rd == idex_rs2) ? 2'b10 :
                       (memwb_reg_write && memwb_rd != 5'b0 && memwb_rd == idex_rs2) ? 2'b01 :
                       2'b00;

    // Value to forward from EX/MEM: either PC+4 (JAL/JALR) or ALU result
    wire [31:0] exmem_fwd_val;
    assign exmem_fwd_val = exmem_pc_to_reg ? (exmem_pc + 4) : exmem_alu_result;

    wire [31:0] ex_fwd_rs1, ex_fwd_rs2;
    assign ex_fwd_rs1 = (forward_a == 2'b10) ? exmem_fwd_val :
                        (forward_a == 2'b01) ? wb_write_data  :
                        idex_RD1;

    assign ex_fwd_rs2 = (forward_b == 2'b10) ? exmem_fwd_val :
                        (forward_b == 2'b01) ? wb_write_data  :
                        idex_RD2;

    // =========================================================================
    // EX Stage — ALU
    // =========================================================================
    wire [31:0] ex_SrcA, ex_alu_result;
    wire [2:0]  ex_flags;

    assign ex_SrcA = (idex_alu_src_a[0]) ? ((idex_alu_src_a[1]) ? idex_pc : 32'b0) : ex_fwd_rs1;

    ALU alu_uut (
        .src_a(ex_SrcA),
        .src_b(idex_alu_src_b ? idex_ext_imm : ex_fwd_rs2),
        .control(idex_alu_control),
        .result(ex_alu_result),
        .flags(ex_flags)
    );

    // =========================================================================
    // EX Stage — Branch / Jump resolution
    // =========================================================================
    wire [1:0]  ex_PC_src;
    wire [31:0] ex_pc_next;

    PC_Logic pcl_uut (
        .PCS(idex_PCS),
        .funct3(idex_funct3),
        .alu_flags(ex_flags),
        .PC_src(ex_PC_src)
    );

    // JALR: target = (rs1 + imm) & ~1  (ALU already computed rs1 + imm)
    // Branch / JAL: target = PC + imm
    assign ex_pc_next = (ex_PC_src == 2'b11) ? (ex_alu_result & 32'hFFFFFFFE) :
                        (idex_pc + idex_ext_imm);

    // =========================================================================
    // EX/MEM Pipeline Register
    // =========================================================================
    reg [31:0] exmem_pc, exmem_alu_result, exmem_RD2;
    reg [4:0]  exmem_rd;
    reg        exmem_mem_to_reg, exmem_reg_write, exmem_pc_to_reg, exmem_mem_write;

    always @(posedge clk) begin
        if (rst) begin
            exmem_pc         <= 32'b0;
            exmem_alu_result <= 32'b0;
            exmem_RD2        <= 32'b0;
            exmem_rd         <= 5'b0;
            exmem_mem_to_reg <= 1'b0;
            exmem_reg_write  <= 1'b0;
            exmem_pc_to_reg  <= 1'b0;
            exmem_mem_write  <= 1'b0;
        end
        else begin
            exmem_pc         <= idex_pc;
            exmem_alu_result <= ex_alu_result;
            exmem_RD2        <= ex_fwd_rs2;     // forwarded store data
            exmem_rd         <= idex_rd;
            exmem_mem_to_reg <= idex_mem_to_reg;
            exmem_reg_write  <= idex_reg_write;
            exmem_pc_to_reg  <= idex_pc_to_reg;
            exmem_mem_write  <= idex_mem_write;
        end
    end

    // =========================================================================
    // MEM Stage — memory access handled externally in Top_MMC
    // =========================================================================

    // =========================================================================
    // MEM/WB Pipeline Register
    // =========================================================================
    reg [31:0] memwb_pc, memwb_alu_result, memwb_mem_read_data;
    reg [4:0]  memwb_rd;
    reg        memwb_mem_to_reg, memwb_reg_write, memwb_pc_to_reg;

    always @(posedge clk) begin
        if (rst) begin
            memwb_pc            <= 32'b0;
            memwb_alu_result    <= 32'b0;
            memwb_mem_read_data <= 32'b0;
            memwb_rd            <= 5'b0;
            memwb_mem_to_reg    <= 1'b0;
            memwb_reg_write     <= 1'b0;
            memwb_pc_to_reg     <= 1'b0;
        end
        else begin
            memwb_pc            <= exmem_pc;
            memwb_alu_result    <= exmem_alu_result;
            memwb_mem_read_data <= mem_read_data;
            memwb_rd            <= exmem_rd;
            memwb_mem_to_reg    <= exmem_mem_to_reg;
            memwb_reg_write     <= exmem_reg_write;
            memwb_pc_to_reg     <= exmem_pc_to_reg;
        end
    end

    // =========================================================================
    // WB Stage — write-back data mux
    // =========================================================================
    wire [31:0] wb_write_data;

    assign wb_write_data = memwb_pc_to_reg  ? (memwb_pc + 4) :
                           memwb_mem_to_reg ? memwb_mem_read_data :
                           memwb_alu_result;

endmodule
