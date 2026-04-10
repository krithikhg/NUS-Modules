`timescale 1ns / 1ps
module Decoder(    
    input [31:0] instr,
    output logic [1:0] PCS,
    output logic mem_to_reg,
    output logic mem_write,
    output logic [3:0] alu_control,
    output logic [1:0] alu_src_a,
    output logic alu_src_b,
    output logic [2:0] imm_src,
    output logic reg_write,
    output logic pc_to_reg
    );
    
    logic [6:0] opcode;
    logic [2:0] funct3;
    logic [6:0] funct7;
    assign opcode = instr[6:0];
    assign funct3 = instr[14:12];
    assign funct7 = instr[31:25];
    
    always @(instr) begin
        case (opcode)
            7'h33: begin // dp reg
                PCS = 2'b00;
                mem_to_reg = 1'b0;
                pc_to_reg = 1'b0;
                reg_write = 1'b1;
                mem_write = 1'b0;
                alu_src_a = 2'b00;
                alu_src_b = 1'b0;
                imm_src = 3'b000;
                alu_control = {funct7[5], funct3};  // fixed
            end

            7'h13: begin // dp imm
                PCS = 2'b00;
                mem_to_reg = 1'b0;
                pc_to_reg = 1'b0;
                reg_write = 1'b1;
                mem_write = 1'b0;
                alu_src_a = 2'b00;
                alu_src_b = 1'b1;
                imm_src = (funct3 == 3'b001 || funct3 == 3'b101) ? 3'b101 : 3'b000;
                alu_control = {(funct3 == 3'd5) ? funct7[5] : 1'b0, funct3};  // fixed
            end

            7'h03: begin //load
                PCS = 2'b00;
                mem_to_reg = 1'b1;
                pc_to_reg = 1'b0;
                reg_write = 1'b1;
                mem_write = 1'b0;
                alu_src_a = 2'b00;
                alu_src_b = 1'b1;
                imm_src = 3'b000;
                alu_control = 4'b0000;
            end

            7'h37: begin //lui
                PCS = 2'b00;
                mem_to_reg = 1'b0;
                pc_to_reg = 1'b0;
                reg_write = 1'b1;
                mem_write = 1'b0;
                alu_src_a = 2'b01;
                alu_src_b = 1'b1;
                imm_src = 3'b011;
                alu_control = 4'b0000;
            end

            7'h17: begin //auipc
                PCS = 2'b00;
                mem_to_reg = 1'b0;
                pc_to_reg = 1'b0;
                reg_write = 1'b1;
                mem_write = 1'b0;
                alu_src_a = 2'b11;
                alu_src_b = 1'b1;
                imm_src = 3'b011;
                alu_control = 4'b0000;
            end

            7'h23: begin //store
                PCS = 2'b00;
                mem_to_reg = 1'b0;
                pc_to_reg = 1'b0;
                reg_write = 1'b0;
                mem_write = 1'b1;
                alu_src_a = 2'b00;
                alu_src_b = 1'b1;
                imm_src = 3'b001;
                alu_control = 4'b0000;
            end

            7'h63: begin //branch
                PCS = 2'b01;
                mem_to_reg = 1'b0;
                pc_to_reg = 1'b0;
                reg_write = 1'b0;
                mem_write = 1'b0;
                alu_src_a = 2'b00;
                alu_src_b = 1'b0;
                imm_src = 3'b010;
                alu_control = 4'b1000;  // SUB for branch comparison
            end

            7'h6F: begin // jal
                PCS = 2'b10;
                mem_to_reg = 1'b0;
                pc_to_reg = 1'b1;
                reg_write = 1'b1;
                mem_write = 1'b0;
                alu_src_a = 2'b00;
                alu_src_b = 1'b0;
                imm_src = 3'b110;
                alu_control = 4'b0000;
            end

            7'h67: begin // jalr
                PCS = 2'b11;
                mem_to_reg = 1'b0;
                pc_to_reg = 1'b1;
                reg_write = 1'b1;
                mem_write = 1'b0;
                alu_src_a = 2'b00;
                alu_src_b = 1'b1;
                imm_src = 3'b000;
                alu_control = 4'b0000;
            end

            default: begin
                PCS = 2'b00;
                mem_to_reg = 1'b0;
                pc_to_reg = 1'b0;
                reg_write = 1'b0;
                mem_write = 1'b0;
                alu_src_a = 2'b00;
                alu_src_b = 1'b0;
                imm_src = 3'b000;
                alu_control = 4'b0000;
            end
        endcase
    end
endmodule