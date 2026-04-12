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
// Description: The main RISC-V CPU
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
    //input Interrupt,      // for optional future use.
    input [31:0] instr,
    input [31:0] mem_read_data,       // v2: Renamed to support lb/lbu/lh/lhu
    output mem_read,
    output mem_write,  // Delete reg for release. v2: Changed to column-wise write enable to support sb/sw. Each column is a byte.
    output [31:0] PC,
    output [31:0] alu_result,
    output [31:0] mem_write_data  // Delete reg for release. v2: Renamed to support sb/sw
    );

	assign mem_read = mem_to_reg; // This is needed for the proper functionality of some devices such as UART CONSOLE
	assign alu_result = result;
	assign PC = pc;
	assign mem_write_data = RD2;

	// Create all the wires/logic signals you need here
	wire [31:0] ext_imm, result, RD1, RD2, pc, SrcA;
	wire [3:0] alu_control;
	wire [2:0] imm_src;
	wire [2:0] flags;
	wire [1:0] PCS;
	wire mem_to_reg, alu_src_b, reg_write, pc_to_reg;
	wire [1:0] alu_src_a, PC_src;

	// Instantiate your extender module here
    Extend extender_uut (
        .instr_imm(instr[31:7]),
        .imm_src(imm_src),
        .ext_imm(ext_imm)
    );

	// Instantiate your instruction decoder here
	Decoder decoder_uut (
        .instr(instr),
        .PCS(PCS),
        .imm_src(imm_src),
        .mem_to_reg(mem_to_reg),
        .mem_write(mem_write),
        .alu_control(alu_control),
        .alu_src_a(alu_src_a),
        .alu_src_b(alu_src_b),
        .reg_write(reg_write),
        .pc_to_reg(pc_to_reg)
    );

    assign SrcA = (alu_src_a[0]) ? ((alu_src_a[1]) ? pc : 32'b0) : RD1;

	// Instantiate your ALU here
	ALU alu_uut (
	   .src_a(SrcA),
	   .src_b(alu_src_b ? ext_imm : RD2),
	   .control(alu_control),
	   .result(result),
	   .flags(flags)
    );

	// Instantiate the Register File
	RegFile reg_uut (
	   .clk(clk),
	   .we(reg_write),
	   .rs1(instr[19:15]),
	   .rs2(instr[24:20]),
	   .rd(instr[11:7]),
	   .WD(pc_to_reg ? (pc + 4) : (mem_to_reg ? mem_read_data : result)),
	   .RD1(RD1),
	   .RD2(RD2)
    );

	// Instantiate the PC Logic
	PC_Logic pcl_uut (
	   .PCS(PCS),
	   .funct3(instr[14:12]),
	   .alu_flags(flags),
	   .PC_src(PC_src)
    );

	// Instantiate the Program Counter
	ProgramCounter counter_uut (
	   .clk(clk),
	   .rst(rst),
	   .pc_in((PC_src == 2'b11) ? (result & 32'hFFFFFFFE) : (pc + (PC_src[0] ? ext_imm : 4))),
	   .pc(pc)
    );

endmodule
