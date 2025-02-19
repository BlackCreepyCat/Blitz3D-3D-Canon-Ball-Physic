Global gravityacc#=0.001,startspeed#=0.6

Global camera

Global rx#,ry#,remx,remy

Global bulletentity

Global angle#,direction#


Type bullet

Field x#,y#,z#
Field ax#,ay#,az#
Field e
Field speed#
Field gravity#

End Type




Graphics3D 640,480,16,2



bulletentity=CreateSphere(4)
HideEntity bulletentity
ScaleEntity bulletentity,0.2,0.2,0.2


camera=CreateCamera()
MoveEntity camera,10,10,10

light=CreateLight(3)
PositionEntity light,10,10,-10



player=CreateCube()
barrel=CreateCube(cube)
PositionMesh barrel,0,0,-1
ScaleEntity barrel,0.1,0.1,1
MoveEntity barrel,0,1,0


PointEntity camera,player
PointEntity light,player

remx=MouseX():remy=MouseY():MoveMouse remx,remy
Repeat


If MouseHit(2) Then remx=MouseX():remy=MouseY():MoveMouse remx,remy

If MouseDown(2)


movecamera(camera,1)

Else

If KeyDown(200) Then angle=angle+1:If angle>90 Then angle=90
If KeyDown(208) Then angle=angle-1:If angle<0 Then angle=0
If KeyDown(203) Then direction=direction+1
If KeyDown(205) Then direction=direction-1
RotateEntity barrel,angle,direction,0

EndIf

If KeyDown(57) Then createbullet(startspeed#,0,1,0,angle,direction,0)
If KeyDown(78) Then startspeed#=startspeed#+0.01:If startspeed#<0.2 Then startspeed#=0.2
If KeyDown(74) Then startspeed#=startspeed#-0.01:If startspeed#>10 Then startspeed#=10



updatebullets()


RenderWorld()

Text 10,10,"Angle :"+angle
Text 10,20,"Direction :"+direction
Text 10,30,"Power :"+startspeed#

Flip

Until KeyDown(1)
End



Function movecamera(cam,sp#)


rx#=rx#+MouseYSpeed()/2
ry#=ry#-MouseXSpeed()/2
MoveMouse remx,remy
If rx#>90 Then rx#=90
If rx#<-90 Then rx#=-90
RotateEntity cam,rx#,ry#,1


If KeyDown( 208 )=True Then MoveEntity cam,0,0,-sp
If KeyDown( 200 )=True Then MoveEntity cam,0,0,sp
If KeyDown( 203 )=True Then MoveEntity cam,-sp,0,0
If KeyDown( 205 )=True Then MoveEntity cam,sp,0,0

End Function


Function createbullet(speed#,x#,y#,z#,ax#,ay#,az#)

b.bullet=New bullet
b\speed#=speed#
b\x#=x#
b\y#=y#
b\z#=z#
b\ax#=ax#
b\ay#=ay#
b\az#=az#
b\e=CopyEntity(bulletentity)
PositionEntity b\e,b\x,b\y,b\z
RotateEntity b\e,b\ax,b\ay,b\az

End Function


Function updatebullets()
For b.bullet=Each bullet
MoveEntity b\e,0,0,-b\speed#
TranslateEntity b\e,0,-b\gravity#,0
b\gravity#=b\gravity#+gravityacc#
If EntityDistance(camera,b\e)>5000 Then FreeEntity b\e:Delete b
Next
End Function
