`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: National University of Singapore
// Engineer: Neil Banerjee
// 
// Create Date: 15.01.2025 15:07:03
// Design Name: 
// Module Name: SevenSegDecoder
// Project Name: CS2100DE Labs 
// Target Devices: Nexys 4
// Tool Versions: Vivado 2023.2
// Description: 
// 
// Dependencies: Nil
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module SevenSegDecoder(
    input [6:0] sw,
    output logic [6:0] seg,
    output [7:0] an
    );
    
    // EXAMPLE OF RTL LOOP. You should not uncomment this code. It is simply to 
    // illustrate how loops can be used to implement repetitive logic. 
    // genvar i;
    // for(i = 0; i < 7; i++) begin
    //     assign an[i] = (sw[6:4] == i) ? 0 : 1;
    // end
        
    // Code for anodes here:
    genvar i;
    for(i=0; i<8; i++) begin
        assign an[i] = (sw[6:4] == i) ? 0 : 1;
    end

    // Code for segments here:
    
    
    always_comb begin
        case (sw[3:0])
            4'd0:    seg[6:0] = 7'b1000000; // 0
            4'd1:    seg[6:0] = 7'b1111001; // 1
            4'd2:    seg[6:0] = 7'b0100100; // 2
            4'd3:    seg[6:0] = 7'b0110000; // 3
            4'd4:    seg[6:0] = 7'b0011001; // 4
            4'd5:    seg[6:0] = 7'b0010010; // 5
            4'd6:    seg[6:0] = 7'b0000010; // 6
            4'd7:    seg[6:0] = 7'b1111000; // 7
            4'd8:    seg[6:0] = 7'b0000000; // 8
            4'd9:    seg[6:0] = 7'b0010000; // 9
            4'd10:   seg[6:0] = 7'b0001000; // A
            4'd11:   seg[6:0] = 7'b0000011; // b
            4'd12:   seg[6:0] = 7'b1000110; // C
            4'd13:   seg[6:0] = 7'b0100001; // d
            4'd14:   seg[6:0] = 7'b0000110; // E
            4'd15:   seg[6:0] = 7'b0001110; // F
            default: seg[6:0] = 7'b1111111; // Default/Off (127)
        endcase
    end
endmodule
