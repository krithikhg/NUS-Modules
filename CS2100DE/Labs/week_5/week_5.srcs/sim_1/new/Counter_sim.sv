`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 02/13/2026 09:49:02 AM
// Design Name: 
// Module Name: Counter_sim
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


module Counter_sim(
        );
        
    logic[7:0] clk;
    logic[1:0] rst;
    logic[1:0]  up_down;
    logic[7:0] count;

    
    Counter uut(clk, rst, up_down, count);
    
    int correct_count = 0;
    
    initial begin
        clk = 0;
        up_down = 0;
        rst = 0;
        #1;
        rst = 1;
        
        for(int i=0; i<20; i++) begin
            if (count != correct_count) begin
                $error("Counts are not equal");
                $stop;
            end
            
            else begin
                correct_count ++;
                #(10 * 25 * 10**6);
            end 
            
        end
        
        up_down = 1;
        for(int i=0; i<20; i++) begin
            if (count != correct_count) begin
                $error("Counts are not equal");
                $stop;
            end
            else begin
                correct_count --;
                #(10 * 25 * 10**6);
            end 
        end
    $finish;   
    end 
    
    always begin
        #5;
        clk = 1;
        #5; 
        clk = 0;
    end
endmodule
