`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 02/13/2026 09:29:13 AM
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
    input clk,
    input [2:0] sw,
    input btnC,
    input btnL,
    input btnR,
    output [6:0] seg,
    output [7:0] an
    );
    
    logic[7:0] count0;
    logic[7:0] count1;
    logic[7:0] count2 ;
    
    
    Counter counter0(clk, !btnL, sw[2], count0);
    Counter counter1(clk, !btnC, sw[1], count1);
    Counter counter2(clk, !btnR, sw[0], count2);
    
    logic[2:0] slow_count;
    
    SlowCounter slow_counter(clk, slow_count);
    
    logic[2:0] anode_count;    
    logic[3:0] hex_num;
    
    SevenSegDecoder seven_seg_decoder({anode_count, hex_num}, seg, an);
     
     always_ff @(posedge clk) begin
         case(slow_count)
            2: anode_count <= 1;
            5: anode_count <= 4;
            default: anode_count <= slow_count;           
         endcase
                
         case(slow_count)
            0: hex_num <= count2[3:0];
            1: hex_num <= count2[7:4];
            
            2: hex_num <= count2[7:4];
            
            3: hex_num <= count1[3:0];
            4: hex_num <= count1[7:4];
            
            5: hex_num <= count1[7:4];
            
            6: hex_num <= count0[3:0];
            7: hex_num <= count0[7:4];
         endcase
     end
endmodule
