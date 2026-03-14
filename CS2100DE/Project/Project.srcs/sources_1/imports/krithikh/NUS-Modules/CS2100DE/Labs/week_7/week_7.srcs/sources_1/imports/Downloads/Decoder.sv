`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: National University of Singapore
// Engineer: Neil Banerjee
// 
// Create Date: 22.02.2025 20:37:13
// Design Name: RISCV-MMC
// Module Name: Decoder 
// Project Name: CS2100DE Labs
// Target Devices: Nexys 4/Nexys 4 DDR
// Tool Versions: Vivado 2023.2
// Description: Instruction decoder and Control Unit for the RISC-V CPU we are building
// 
// Dependencies: Nil
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module Decoder(    
    input [31:0] instr,
    output logic [2:0] PCS,
    output logic mem_to_reg,
    output logic mem_write,
    output logic [3:0] alu_control,
    output logic alu_src_b,
    output logic [2:0] imm_src,
    output logic reg_write
    );
    
    logic [6:0] opcode;
    logic [2:0] funct3;
    logic [6:0] funct7;

    assign opcode = instr[6:0]; 
    assign funct3 = instr[14:12];
    assign funct7 = instr[31:25];
    
    always @(instr) begin
    
        mem_to_reg = (opcode == 8'h03) ? 1 : 0;
        reg_write = (opcode == 8'h33 || opcode == 8'h13 || opcode == 8'h03) ? 1 : 0;
        mem_write = (opcode == 8'h23) ? 1 : 0;
        alu_src_b = (opcode == 8'h13 || opcode == 8'h03 || opcode == 8'h23) ? 1 : 0;
        
        case (opcode)
            8'h33: begin // R type
                alu_control = {funct3, funct7[5]};
            end
            8'h13: begin // I type
                alu_control = {funct3, {(funct3 == 4'h5) ? funct7[5] : 1'b0}};
                imm_src = 3'b000;
            end
            8'h03: begin // I type
                alu_control = 4'b0000;
                imm_src = 3'b000;
            end
            8'h23: begin // S type
                alu_control = 4'b0000;
                imm_src = 3'b001;
            end
            8'h63: begin // B type
                alu_control = 4'b0001; 
                imm_src = 3'b010;
            end
            8'h6F: begin // J type
                imm_src = 3'b100;
            end
            8'h37: begin // U type
                imm_src = 3'b011;
            end    
        endcase
        
        PCS = 2'b00;
        
        case(opcode)
            8'h63: begin
                PCS = (funct3 == 0) ? 2'b01 : 2'b00;
            end
            8'h6F: begin
                PCS = 2'b10;
            end
        endcase
    end
    
endmodule
