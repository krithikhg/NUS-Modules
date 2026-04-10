`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: National University of Singapore
// Engineer: Neil Banerjee
// 
// Create Date: 05.03.2025 23:43:42
// Design Name: RISCV-MMC
// Module Name: Extend
// Project Name: CS2100DE Labs
// Target Devices: Nexys 4/Nexys 4 DDR
// Tool Versions: Vivado 2023.2
// Description: Module for extending immediates
// 
// Dependencies: Nil
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module Extend(
    input [31:7] instr_imm,
    input [2:0] imm_src,
    output reg [31:0] ext_imm
    );
    
    always @(imm_src, instr_imm) begin
        case (imm_src)
            3'b000: ext_imm <= {{20{instr_imm[31]}}, instr_imm[31:20]};           // I-type
            3'b001: ext_imm <= {{20{instr_imm[31]}}, instr_imm[31:25], instr_imm[11:7]}; // S-type
            3'b010: ext_imm <= {{19{instr_imm[31]}}, instr_imm[31], instr_imm[7], instr_imm[30:25], instr_imm[11:8], 1'b0}; // B-type
            3'b011: ext_imm <= {instr_imm[31:12], 12'b0};                         // U-type
            3'b100: ext_imm <= {{11{instr_imm[31]}}, instr_imm[31], instr_imm[19:12], instr_imm[20], instr_imm[30:21], 1'b0}; // J-type
            3'b101: ext_imm <= {27'b0, instr_imm[24:20]};                         // shamt (slli/srli/srai)
            3'b110: ext_imm <= {{11{instr_imm[31]}}, instr_imm[31], instr_imm[19:12], instr_imm[20], instr_imm[30:21], 1'b0}; // JAL
            default: ext_imm <= 32'b0;
        endcase
    end
endmodule
