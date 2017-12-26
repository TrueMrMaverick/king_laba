package com.company.Models;

import com.company.DTO.AnimationDTO;
import com.company.Math.AffineTransform;
import com.company.Math.Matrix;
import com.company.Math.Vector;
import com.company.Services.AnimationService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.PI;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

/**
 * Created by Nikita on 24.12.2017.
 */
public class Model2D {
    public String modelName;
    public Matrix vertices;
    public Matrix edges;
    private String path;
    private boolean isAnimated;

    private AnimationService animationService = new AnimationService();



    public Model2D(){
        modelName = "";
        path = "";
        vertices = new Matrix();
        edges = new Matrix();
        isAnimated = false;
    }


    public Model2D(String path, String modelName, File[] modelProperties){
        this.path = path;
        this.modelName = modelName;
        vertices = new Matrix(fileMatrixInit("Vertices"));
        edges = new Matrix(fileMatrixInit("Edges"));

        for (File file:
             modelProperties) {
            String fileName = file.getName();
            if(file.getName().equals("Animation.txt")){
                isAnimated = true;
                animation(file);
            }
        }


    }

    private Matrix fileMatrixInit(String fileName){
        File file = new File(path + fileName + ".txt");
        ArrayList<String> stringList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader out = new BufferedReader(fileReader);

            try {
                String stringBuffer;
                while ((stringBuffer = out.readLine()) != null){
                    stringList.add(stringBuffer);
                }
            } finally {
                out.close();
            }

        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Integer> intBuffer = new ArrayList<>();
        Matrix matrix = new Matrix();
        for (int i = 0; i < stringList.size(); i++){
            String subString = stringList.get(i);
            String temp = new String();
            for(int j = 0; j < subString.length(); j++){
                if(subString.charAt(j) != ' '){
                    temp += subString.charAt(j);
                } else {
                    intBuffer.add(Integer.parseInt(temp));
                    temp = "";
                }
            }
            if(temp != ""){
                intBuffer.add(Integer.parseInt(temp));
            }

            if (i == 0){
                int row = intBuffer.get(0);
                int col = intBuffer.get(1);

                matrix = new Matrix(row, col);
            } else {
                List<Double> doubleBuffer = new ArrayList<>();
                for (int idx = 0; idx < intBuffer.size(); idx++){
                    doubleBuffer.add((double) intBuffer.get(idx));
                }
                Vector line = new Vector(doubleBuffer);
                matrix.setRow(i - 1, line);
            }
            intBuffer.clear();
        }

        return matrix;
    }

    public void animation(ActionListener actionListener){
        Timer timer = new Timer(0, actionListener);
        timer.start();
    }

    public void animation(int delay, ActionListener actionListener){
        Timer timer = new Timer(delay, actionListener);
        timer.start();
    }

    public void animation(File animationFile){

        ArrayList<AnimationDTO> animationList = animationService.getModelAnimations(animationFile);

        Timer timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (AnimationDTO animation:
                        animationList) {
                    List<String> params = animation.getParams();
                    if (animation.getType().equals("move")){
                        vertices = animationService.move(vertices, Double.parseDouble(params.get(0)), Double.parseDouble(params.get(1)));
                    } else if (animation.getType().equals("turn")){
                        if(params.size() == 2){
                            vertices = animationService.turn(vertices, Double.parseDouble(params.get(0)), Integer.parseInt(params.get(1)));
                        } else {
                            vertices = animationService.turn(vertices, Double.parseDouble(params.get(0)), Double.parseDouble(params.get(1)), Integer.parseInt(params.get(2)));
                        }
                    } else if (animation.getType().equals("scale")){
                        vertices = animationService.scale(vertices, Double.parseDouble(params.get(0)), Double.parseDouble(params.get(1)));
                    } if (animation.getType().equals("reflect")){
                        vertices = animationService.reflect(vertices);
                    }
                }
            }
        });
        timer.start();
    }
}
