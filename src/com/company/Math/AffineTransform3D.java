package com.company.Math;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.sqrt;

/**
 * Created by Nikita on 26.12.2017.
 */
public class  AffineTransform3D {
    public Matrix translation(double x, double y, double z){
        double[] T = {
                1, 0, 0, x,
                0, 1, 0, y,
                0, 0, 1, z,
                0, 0, 0, 1};
        return new Matrix(4, 4, T);
    }

    //тождественное АП;
    public Matrix Identity() {
        double[] T = {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        return new Matrix(4, 4, T);
    }

    // поворот на угол t вокруг начала координат против часовой стрелки;

    public Matrix rotationX(double t) {
        double[] T = {
                1, 0, 0, 0,
                0, cos(t), -sin(t), 0,
                0, sin(t), cos(t), 0,
                0, 0, 0, 1};
        return new Matrix(4, 4, T);
    }

    public Matrix rotationY(double t) {
        double[] T = {
                cos(t), 0, sin(t), 0,
                0, 1, 0, 0,
                -sin(t), 0, cos(t), 0,
                0, 0, 0, 1};
        return new Matrix(4, 4, T);
    }

    public Matrix rotationZ(double t) {
        double[] T = {
                cos(t), -sin(t), 0, 0,
                sin(t), cos(t), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        return new Matrix(4, 4, T);
    }

    // Rotation(c, s) - поворот на угол, косинус и синус которого пропорциональны величинам c и s;
    // поворот на угол t вокруг начала координат против часовой стрелки;
    public Matrix rotationX(double c, double s) {
        double k = 1/sqrt(c*c + s*s);
        double[] T = {
                1, 0, 0, 0,
                0, k*c, -k*s, 0,
                0, k*s, k*c, 0,
                0, 0, 0, 1};
        return new Matrix(4, 4, T);
    }

    public Matrix rotationY(double c, double s) {
        double k = 1/sqrt(c*c + s*s);
        double[] T = {
                k*c, 0, k*s, 0,
                0, 1, 0, 0,
                -k*s, 0, k*c, 0,
                0, 0, 0, 1};
        return new Matrix(4, 4, T);
    }

    public Matrix rotationZ(double c, double s) {
        double k = 1/sqrt(c*c + s*s);
        double[] T = {
                k*c, -k*s, 0, 0,
                k*s, k*c, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        return new Matrix(4, 4, T);
    }


    // Scaling(kx, ky) - масштабирование;
    public Matrix scaling(double kx, double ky, double kz) {
        double[] T = {
                kx, 0, 0, 0,
                0, ky, 0, 0,
                0, 0, kz, 0,
                0, 0,  0, 1};
        return new Matrix(4, 4, T);
    }


}
