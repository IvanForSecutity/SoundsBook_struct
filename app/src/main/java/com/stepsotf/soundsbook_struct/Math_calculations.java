package com.stepsotf.soundsbook_struct;

import java.util.Random;

/**
 * Created by v0090 on 26.11.2016.
 */

class Math_calculations {
    float[] coords=new float[8];
    int iterator;
    float Width,Height,PicSize;
    public static int [] GetRandomCoords(float screensize[],int pics_num)
    {
        return  new int[]{100,100};
    }

    public float[] getCoords(float width,float height,float picSize)
    {
        Width = width;
        Height = height;
        PicSize = picSize;
        //oneCoord ���������� 2 ���� ��� ���������� � ������� ������������
        int s = oneCoord(0);
        while(oneCoord(0)!=2) {
            oneCoord(0);
        }
        return coords;
    }
    int oneCoord(int _iterator)
    {
        Random rand = new Random();
        if(_iterator == 4) return 2;

        //test with others
//Error case :(coords[_iterator*2]>coords[i*2] && coords[_iterator*2] < (coords[i*2]+PicSize) && coords[_iterator*2+1]>coords[i*2+1] && coords[_iterator*2+1] < (coords[i*2+1]+PicSize))
        int not_ok=1;//num of sovpadeniy
        if (_iterator == 0) {
            coords[_iterator * 2] = rand.nextFloat() * (Width - PicSize);
            coords[_iterator * 2 + 1] = rand.nextFloat() * (Height - PicSize);
        } else
            while (not_ok > 0) {
                not_ok=0;
                coords[_iterator * 2] = rand.nextFloat() * (Width - PicSize);
                coords[_iterator * 2 + 1] = rand.nextFloat() * (Height - PicSize);
                for (int i = 0; i < _iterator; i++) {
                    //new coords for current pic
                    float x_cur = coords[_iterator * 2],
                            y_cur = coords[_iterator * 2 + 1],
                            x_list = coords[i * 2],
                            y_list = coords[i * 2 + 1];
                    if ((x_cur + PicSize) > x_list &&
                            x_cur < (x_list + PicSize) &&
                            (y_cur + PicSize) > y_list &&
                            y_cur < (y_list + PicSize)) {
                        not_ok++;
                        if (not_ok > 20) {
                            return 3;
                        }
                        break;
                    }
                    else not_ok = 0;

                }
            }

        return oneCoord(_iterator+1);
    }
}
