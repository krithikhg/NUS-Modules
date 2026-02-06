`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 02/06/2026 10:20:10 AM
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
    input [6:0] sw,
    output [6:0] seg,
    output [7:0] an
    );
    
    SevenSegDecoder seven_seg_decoder(
        .sw (sw[6:0]),
        .seg (seg[6:0]),
        .an (an[7:0])    
    );
endmodule
