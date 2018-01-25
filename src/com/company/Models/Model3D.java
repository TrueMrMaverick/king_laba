package com.company.Models;

import com.company.Math.Matrix;
import com.company.Math.Vector;
import com.company.Services.ModelService;

/**
 * Created by Nikita on 26.12.2017.
 */
public class Model3D {

    private Matrix vertices;
    private Matrix initialVertices;
    private Matrix projectedVertices;
    private Matrix verges;
    private Matrix edges;
    private String path;

    private ModelService modelService = new ModelService();



    public Model3D() {
        vertices = new Matrix();
        initialVertices = new Matrix();
        projectedVertices = new Matrix();
        verges = new Matrix();
        edges = new Matrix();
        path = "";
    }

    public Model3D(Matrix vertices, Matrix verges) {
        this.vertices = vertices.clone();
        projectedVertices = new Matrix();
        this.verges = verges.clone();
    }

    public Model3D(String path, String verticesFileName, String vergesFileName){
        this.path = path;
        vertices = new Matrix(modelService.fileMatrixInit(path + verticesFileName));
        verges = new Matrix(modelService.fileMatrixInit(path + vergesFileName));
        projectedVertices = new Matrix();
        vertices.print("Vertices: ");
        verges.print("Verges: ");
        setEdges();
    }

    public void setEdges(){
        Matrix edges = new Matrix(vertices.getRowSize(), verges.getColSize());
        for (int i = 0; i < verges.getColSize(); i++){
            double point1 = verges.getElement(i, 0);
            double point2 = verges.getElement(i, 1);
            edges.setElement((int) point1 - 1, (int) point2 - 1, 1);
            edges.setElement((int) point2 - 1, (int) point1 - 1, 1);
            point2 = verges.getElement(i, 2);
            edges.setElement((int) point1 - 1, (int) point2 - 1, 1);
            edges.setElement((int) point2 - 1, (int) point1 - 1, 1);
            point1 = verges.getElement(i, 1);
            edges.setElement((int) point1 - 1, (int) point2 - 1, 1);
            edges.setElement((int) point2 - 1, (int) point1 - 1, 1);
        }

        for (int i = 0; i < edges.getColSize(); i++) {
            boolean isNullRow = true;
            for (int j = 0; j < edges.getRowSize(); j++) {
                if(edges.getElement(i, j) != 0){
                    isNullRow = false;
                    break;
                }
            }
            if(isNullRow){
                edges.removeRow(i);
            }
        }

        this.edges = edges;
        edges.print("Edges: ");
    }

    public void project(Matrix matrix){
        projectedVertices = matrix.mult(vertices);
    }

    public void applyAffineTransform(Matrix affineTransform, Matrix worldToProject){
        vertices = affineTransform.mult(vertices);
        this.project(worldToProject);
    }

    public double getVertexX(int index) {
        return  vertices.getElement(0, index) / vertices.getElement(3, index);
    }
    public double getVertexY(int index) {
        return  vertices.getElement(1, index) / vertices.getElement(3, index);
    }
    public double getVertexZ(int index) {
        return  vertices.getElement(2, index) / vertices.getElement(3, index);
    }



    public Matrix getVertices() {
        return vertices;
    }

    public void setVertices(Matrix vertices) {
        this.vertices = vertices;
    }

    public Matrix getInitialVertices() {
        return initialVertices;
    }

    public void setInitialVertices(Matrix initialVertices) {
        this.initialVertices = initialVertices;
    }

    public Matrix getProjectedVertices() {
        return projectedVertices;
    }

    public boolean projectedVerticesIsEmpty(){
        return projectedVertices.isEmpty();
    }

    public void setProjectedVertices(Matrix projectedVertices) {
        this.projectedVertices = projectedVertices;
    }

    public Matrix getVerges() {
        return verges;
    }

    public void setVerges(Matrix verges) {
        this.verges = verges;
    }

    public Matrix getEdges() {
        return edges;
    }

    public void setEdges(Matrix edges) {
        this.edges = edges;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
