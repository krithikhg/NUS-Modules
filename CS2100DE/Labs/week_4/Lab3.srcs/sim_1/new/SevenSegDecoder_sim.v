`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 02/06/2026 10:22:31 AM
// Design Name: 
// Module Name: SevenSegDecoder_sim
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


module SevenSegDecoder_sim(

    );
    
    logic[6:0] sw;
    logic[6:0] seg;
    logic[7:0] an;
    
    SevenSegDecoder uut (sw, seg, an);
    
    initial begin
        for(int i=0; i<16; i++) begin
            assign sw[3:0] = i;
            #10;
        end
        
        for(int j=0; j<8; j++) begin
            assign sw[6:4] = j;
            #10;
         end
        sw = 7'b0;
        seg = 7'b0;
        an = 8'b0;
    end
endmodule
