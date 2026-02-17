`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 02/17/2026 01:22:46 PM
// Design Name: 
// Module Name: SlowCounter
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


module SlowCounter(
    input clk,
    output reg [2:0] count
    );
    
    localparam N_COUNTS = (10**8) / (500); //for 4Hz, change denominator according to desired frequency
    localparam N_BITS  = $clog2(N_COUNTS); //automatically calculate bus size for storing clock counter

    logic[N_BITS-1:0] clk_cnt = 0;
    
    always @(posedge clk) begin
        clk_cnt <= (clk_cnt == N_COUNTS-1) ? 0 : clk_cnt+1;
    end
 
    always @(posedge clk) begin
        if(clk_cnt == 0) begin
            count <= count + 1;
        end  
    end
endmodule
