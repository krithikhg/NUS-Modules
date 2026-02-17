`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 02/13/2026 09:36:03 AM
// Design Name: 
// Module Name: Counter
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

module Counter( 
    input clk, 
    input rst, 
    input up_down, 
    output reg [7:0] count
); 

localparam N_COUNTS = (10**8) / (4); //for 4Hz, change denominator according to desired frequency
localparam N_BITS  = $clog2(N_COUNTS); //automatically calculate bus size for storing clock counter

logic[N_BITS-1:0] clk_cnt = 0;

always @(posedge clk) begin
    clk_cnt <= (clk_cnt == N_COUNTS-1) ? 0 : clk_cnt+1;
    if(rst==0) begin
        clk_cnt <= 0;
    end
 
 end
 
 always @(posedge clk) begin
    if(clk_cnt == 0) begin
    
        if(up_down ==0) begin 
            count <= count + 1; 
        end 
        else begin 
            count <= count - 1; 
        end 
    end 
    
    if(rst == 0) begin 
        count <= 0;
    end 
end
endmodule 