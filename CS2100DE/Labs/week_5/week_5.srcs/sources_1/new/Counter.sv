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
    assign count = 0;
    int real_counter = 0;
    localparam N_COUNT = 25*(10**6);
    localparam N_BITS = 25;
    logic[N_BITS-1:0] clk_cnt;
    
    
    
    always @(posedge clk) begin
        
        if(!rst) begin
            clk_cnt <= 0;
            count <= 0;
        end 
        else 
            if(clk_cnt == N_COUNT) begin
                clk_cnt <= 0;
            end 
            if(clk_cnt == 0) begin
                if(up_down) begin
                    count <= count + 1;
                end
                else begin
                    count <= count -1;
                end
                if(count == 256) begin
                    count <= 0;
                end
                if(count == -1) begin
                    count <= 255;
                end
             end
         end
    
    
    
    
endmodule
