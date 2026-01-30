`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 01/30/2026 09:49:32 AM
// Design Name: 
// Module Name: Top
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module Top(
    input [15:0] sw,
    output [8:0] led
    );
    Adder adder_1(
        .in_a (sw[15:8]),
        .in_b (sw[7:0]),
        .result (led[7:0]),
        .carry_out (led[8])
    );
endmodule
