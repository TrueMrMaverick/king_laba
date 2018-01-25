package com.company.Cameras;

import com.company.Math.Matrix;
import com.company.Math.Vector;

import javax.swing.*;

/**
 * Created by Nikita on 26.12.2017.
 */
public class Camera3D extends Camera2D {

    private Vector Ov;
    private Vector top;
    private Vector n;
    private double d;
    private Matrix worldToView;
    private Matrix worldToProject;
    private Matrix viewToProject;

    public Camera3D() {}

    public Camera3D(JPanel jPanel) {
        super(jPanel);
    }

    public Camera3D(JPanel jPanel, double L, double R, double B, double T){
        super(jPanel, L, R, B, T);

        setOv(0, 0, 0);
        setTop(0, 1, 0);
        setN(0, 0, 1);
        setD(10);
        updateCamera();
    }

    private void updateCamera() {
        Vector v1 = top.vectorProduct3D(n);
        Vector kv = n.constMult(1 / n.norm());
        Vector iv = v1.constMult(1 / v1.norm());
        Vector jv = kv.vectorProduct3D(iv);

        // S(w->v)

        double[] q = {
                iv.get(0), iv.get(1), iv.get(2), -(iv.scolarMult(Ov)),
                jv.get(0), jv.get(1), jv.get(2), -(jv.scolarMult(Ov)),
                kv.get(0), kv.get(1), kv.get(2), -(kv.scolarMult(Ov)),
                0, 0, 0, 1
        };

        worldToView = new Matrix(4, 4,q);

        // S(v->p)

        double[] w = {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 0, 1
        };

        viewToProject = new Matrix(4, 3, w);

        if (d > 0){
            viewToProject.setElement(2, 2, - 1 / d);
        }

        // S(w->p)

        worldToProject = viewToProject.mult(worldToView);


    }

    public void changeSize(int delta, int x, int y){
        if (delta > 0){
            setD(d + 0.5);
        } else {
            setD(d - 0.5);
            updateCamera();
        }

    }

    public void setD(double d) {
        this.d = d;
    }

    public void setN(double a, double b, double c) {
        n = new Vector();
        n.add(a);
        n.add(b);
        n.add(c);
    }

    public void setTop(double a, double b, double c) {
        top = new Vector();
        top.add(a);
        top.add(b);
        top.add(c);
    }

    public void setOv(double a, double b, double c) {
        Ov = new Vector();
        Ov.add(a);
        Ov.add(b);
        Ov.add(c);
    }

    public Matrix getWorldToProject(){
        return worldToProject;
    }

}
