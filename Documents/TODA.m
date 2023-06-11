clear all
clc;
N=200;
rxSensorX=randn(1,N);
rxSensorY=randn(1,N);
sourceX=0.5;
sourceY=-0.2;
rxSensorX=[1,1,1,1];
rxSensorY=[2,4,6,8];
sourceX=-0.486;
sourceY=1.5;
noiseVariance=0.0;
noise=noiseVariance*randn(1,N);
[rel_source_pos,error_func]=getSouceWithTDOA(rxSensorX,rxSensorY,sourceX,sourceY,noise)
RangeVector=sqrt((rxSensorX-sourceX).^2+(rxSensorY-sourceY).^2);
RangeVector=[0.6003,2.48668,4.4590,6.5170];
source_pos=getSouceWithTDOAwithRange(rxSensorX,rxSensorY,RangeVector,4)
function [xhat,error]=getSouceWithTDOA(rxSensorX,rxSensorY,sourceX,sourceY,noiseVec)  % Assuming first receiver is the ref receiver
    N=length(rxSensorX);
    rel_rxSensorX=rxSensorX(2:end)-rxSensorX(1);
    rel_rxSensorY=rxSensorY(2:end)-rxSensorY(1);
    rel_sourceX=sourceX-rxSensorX(1);
    rel_sourceY=sourceY-rxSensorY(1);   
    rel_sourceRange=sqrt(rel_sourceX.^2+rel_sourceY.^2);
    R_true=sqrt((rxSensorX(2:end)-sourceX).^2+(rxSensorY(2:end)-sourceY).^2)
    R=R_true-rel_sourceRange+noiseVec(1:(N-1));
    b=0.5*[rel_rxSensorX.^2+rel_rxSensorY.^2-R.^2];
    A=[rel_rxSensorX;rel_rxSensorY;R]';    
    xhat=pinv(A)*b';    
    error=sqrt((xhat(1)-rel_sourceX)^2+(xhat(2)-rel_sourceY)^2);
    xhat(1)=xhat(1)+rxSensorX(1);
    xhat(2)=xhat(2)+rxSensorY(1);
end
function xhat=getSouceWithTDOAwithRange(rxSensorX,rxSensorY,RangeVector,refIndex)  % Assuming Last receiver is the ref receiver
%     refIndex=1;
    RangeVector([refIndex 4]) = RangeVector([4 refIndex])
    rxSensorX([refIndex 4]) = rxSensorX([4 refIndex])
    rxSensorY([refIndex 4]) = rxSensorY([4 refIndex])
    N=length(rxSensorX);
    rel_rxSensorX=rxSensorX(1:end-1)-rxSensorX(end);
    rel_rxSensorY=rxSensorY(1:end-1)-rxSensorY(end);  
    R_rel=RangeVector(1:end-1)-RangeVector(end);
    b=0.5*(rel_rxSensorX.^2+rel_rxSensorY.^2-R_rel.^2);
    A=[rel_rxSensorX;rel_rxSensorY;R_rel]';
    xhat=pinv(A)*b';
    xhat(1)=xhat(1)+rxSensorX(end);
    xhat(2)=xhat(2)+rxSensorY(end);
end