package mapvis.graphic.RegionRendering;

import javafx.geometry.Point2D;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Pair;
import mapvis.common.datatype.INode;
import mapvis.graphic.HexagonalTilingView;
import mapvis.models.BoundaryShape;
import mapvis.models.ConfigurationConstants;
import mapvis.models.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class RegionAreaRenderer {

    private final GraphicsContext graphicsContext;
    private final IRegionPathGenerator regionBoundaryPointsGenerator;
    private final BiConsumer<List<BoundaryShape>, Color> directPolyLineRenderer;
    private final BiConsumer<List<BoundaryShape>, Color> bezierCurveRenderer;
    private final BiConsumer<List<BoundaryShape>, Color> quadricCurveRenderer;
    private final IRegionPathGenerator originalBorderPointsGenerator;
    private final HexagonalTilingView view;
    public RegionAreaRenderer(GraphicsContext graphicsContext, HexagonalTilingView view) {
        this.graphicsContext = graphicsContext;

//        this.regionBoundaryPointsGenerator = new SimplifiedRegionPathGenerator(graphicsContext,
//                ConfigurationConstants.SIMPLIFICATION_TOLERANCE, ConfigurationConstants.USE_HIGH_QUALITY_SIMPLIFICATION);
//        this.regionBoundaryPointsGenerator = new MovingAverageRegionPathGenerator(2);
        this.regionBoundaryPointsGenerator = new DirectRegionPathGenerator(graphicsContext);

        this.originalBorderPointsGenerator = new DirectRegionPathGenerator(graphicsContext);

        this.view = view;

        this.quadricCurveRenderer = (boundaryShapesOfRegion, fillColor) -> {

            Point2D firstPoint = null;
            Point2D currentPoint = null;
            Point2D nextPoint = null;
            boolean firstRenderPass = true;

            for (int i = 0; i < boundaryShapesOfRegion.size(); i++) {
                BoundaryShape boundaryShape = boundaryShapesOfRegion.get(i);
                if(boundaryShape.getShapeLength() == 2){
//                    graphicsContext.moveTo(boundaryShape.getXCoordinateAtIndex(0), boundaryShape.getYCoordinateAtIndex(0));
//                    graphicsContext.lineTo(boundaryShape.getXCoordinateAtIndex(1), boundaryShape.getYCoordinateAtIndex(1));
                    continue;
                }

                double xMid = 0;
                double yMid = 0;

                for (int j = 0; j < boundaryShape.getShapeLength() - 2; j++) {
                    if(firstRenderPass){
                        xMid = (boundaryShape.getXCoordinateAtIndex(0) + boundaryShape.getXCoordinateAtIndex(1)) / 2;
                        yMid = (boundaryShape.getYCoordinateAtIndex(0) + boundaryShape.getYCoordinateAtIndex(1)) / 2;

                        graphicsContext.moveTo(xMid, yMid);
                        firstPoint = new Point2D(xMid, yMid);
                        firstRenderPass = false;
                    }

                    xMid = (boundaryShape.getXCoordinateAtIndex(j) + boundaryShape.getXCoordinateAtIndex(j + 1)) / 2;
                    yMid = (boundaryShape.getYCoordinateAtIndex(j) + boundaryShape.getYCoordinateAtIndex(j + 1)) / 2;

                    graphicsContext.quadraticCurveTo(boundaryShape.getXCoordinateAtIndex(j), boundaryShape.getYCoordinateAtIndex(j), xMid, yMid);
                }
//                xMid = (xMid + nextPoint.getX())/2;
//                yMid = (yMid + nextPoint.getY())/2;
//                graphicsContext.quadraticCurveTo(xMid, yMid, nextPoint.getX(), nextPoint.getY());
//                graphicsContext.setFill(Color.RED);
//                graphicsContext.fillOval(xMid, yMid, 4, 4);
//
//                graphicsContext.setFill(Color.YELLOW);
//                graphicsContext.fillOval(nextPoint.getX(), nextPoint.getY(), 4, 4);
            }

//            graphicsContext.quadraticCurveTo(nextPoint.getX(), nextPoint.getY(), firstPoint.getX(), firstPoint.getY());
        };
//
//        this.quadricCurveRenderer = (shapePointArr, fillColor) -> {
//
//            Point2D firstPoint = null;
//            Point2D currentPoint = null;
//            Point2D nextPoint = null;
//            boolean firstRenderPass = true;
//
//            for (int i = 0; i < shapePointArr.size(); i++) {
//                Point2D[] point2Ds = shapePointArr.get(i);
//                if(point2Ds.length == 2){
////                    graphicsContext.moveTo(point2Ds[0].getX(), point2Ds[0].getY());
////                    graphicsContext.lineTo(point2Ds[1].getX(), point2Ds[1].getY());
//                    continue;
//                }
//
//                double xMid = 0;
//                double yMid = 0;
//
//                for (int j = 0; j < point2Ds.length - 2; j++) {
//                    if(firstRenderPass){
//                        xMid = (point2Ds[0].getX() + point2Ds[1].getX()) / 2;
//                        yMid = (point2Ds[0].getY() + point2Ds[1].getY()) / 2;
//
//                        graphicsContext.moveTo(xMid, yMid);
//                        firstPoint = new Point2D(xMid, yMid);
//                        firstRenderPass = false;
//                    }
//                    currentPoint = point2Ds[j];
//                    nextPoint = point2Ds[j + 1];
//                    xMid = (currentPoint.getX() + nextPoint.getX()) / 2;
//                    yMid = (currentPoint.getY() + nextPoint.getY()) / 2;
//                    graphicsContext.quadraticCurveTo(currentPoint.getX(), currentPoint.getY(), xMid, yMid);
//                }
//                xMid = (xMid + nextPoint.getX())/2;
//                yMid = (yMid + nextPoint.getY())/2;
//                graphicsContext.quadraticCurveTo(xMid, yMid, nextPoint.getX(), nextPoint.getY());
//                graphicsContext.setFill(Color.RED);
//                graphicsContext.fillOval(xMid, yMid, 4, 4);
//
//                graphicsContext.setFill(Color.YELLOW);
//                graphicsContext.fillOval(nextPoint.getX(), nextPoint.getY(), 4, 4);
//            }
//
////            graphicsContext.quadraticCurveTo(nextPoint.getX(), nextPoint.getY(), firstPoint.getX(), firstPoint.getY());
//        };

        this.directPolyLineRenderer = (boundaryShapesOfRegion, fillColor) -> {

            boolean firstRenderPass = true;
            for (BoundaryShape boundaryShape : boundaryShapesOfRegion) {
                for (int i = 0; i < boundaryShape.getXCoords().length; i++) {
                    if(firstRenderPass){
                        graphicsContext.moveTo(boundaryShape.getXCoordinateAtIndex(i), boundaryShape.getYCoordinateAtIndex(i));
                        firstRenderPass = false;
                    }else{
                        graphicsContext.lineTo(boundaryShape.getXCoordinateAtIndex(i), boundaryShape.getYCoordinateAtIndex(i));
                    }
                }
            }
        };

        this.bezierCurveRenderer = (boundaryShapesOfRegion, fillColor) -> {
            drawSpline(graphicsContext, boundaryShapesOfRegion, fillColor, ConfigurationConstants.BEZIER_CURVE_SMOOTHNESS);
        };
    }


    public void drawArea(final IRegionStyler<INode> regionStyler, final Region<INode> regionToDraw, final List<List<BoundaryShape>> regionBoundaryShapes) {
        Color regionFillColor = regionStyler.getColor(regionToDraw);
        graphicsContext.setFill(regionFillColor);
        graphicsContext.setFillRule(FillRule.EVEN_ODD);

        if (regionBoundaryShapes.size() == 0)
            return;

        regionBoundaryShapes.sort((o1, o2) -> o2.size() - o1.size());
        graphicsContext.beginPath();

        for (List<BoundaryShape> regionBoundaryShape : regionBoundaryShapes) {

            if (regionBoundaryShape.size() == 0)
                continue;

            List<Point2D[]> shapePoints = regionBoundaryPointsGenerator.generatePathForBoundaryShape(regionBoundaryShape);
            if(ConfigurationConstants.USE_BEZIER_CURVE){
//                this.bezierCurveRenderer.accept(regionBoundaryShape, regionFillColor);
                this.quadricCurveRenderer.accept(regionBoundaryShape, regionFillColor);
            }else{
                this.directPolyLineRenderer.accept(regionBoundaryShape, regionFillColor);
            }
        }

        graphicsContext.setLineCap(StrokeLineCap.ROUND);
        graphicsContext.setLineJoin(StrokeLineJoin.ROUND);
        if(ConfigurationConstants.DRAW_ORIGINAL_SHAPE){
            this.graphicsContext.setStroke(Color.RED);
        }else{
            this.graphicsContext.setStroke(Color.BLACK);
        }


        graphicsContext.stroke();
        if(ConfigurationConstants.FILL_SHAPE){
            graphicsContext.fill();
        }

        if(!ConfigurationConstants.DRAW_ORIGINAL_SHAPE)
            return;

        graphicsContext.beginPath();
        for (List<BoundaryShape> regionBoundaryShape : regionBoundaryShapes) {

            if (regionBoundaryShape.size() == 0)
                continue;

            List<Point2D[]> originalShapePoints = originalBorderPointsGenerator.generatePathForBoundaryShape(regionBoundaryShape);
//            this.bezierCurveRenderer.accept(shapePoints);
            this.directPolyLineRenderer.accept(regionBoundaryShape, regionFillColor);
            this.graphicsContext.setStroke(Color.BLACK);
        }

        graphicsContext.setLineCap(StrokeLineCap.ROUND);
        graphicsContext.setLineJoin(StrokeLineJoin.ROUND);
        graphicsContext.stroke();
//        graphicsContext.fill();
    }

    private void drawSpline(GraphicsContext graphicsContext, List<BoundaryShape> boundaryShapesOfRegion, Color fillColor, float bezierCurveSmoothness) {
        List<Point2D> cp = new ArrayList<>();   // array of control points, as x0,y0,x1,y1,...

        List<Point2D> inputPointList = new ArrayList<>();
        BoundaryShape lastBoundaryShape = boundaryShapesOfRegion.get(boundaryShapesOfRegion.size() - 1);
        Point2D lastPoint = new Point2D(lastBoundaryShape.getXCoordinateEndpoint(), lastBoundaryShape.getYCoordinateEndpoint());
        inputPointList.add(lastPoint);

        for (int i = 0; i < boundaryShapesOfRegion.size(); i++) {
            BoundaryShape boundaryStep = boundaryShapesOfRegion.get(i);
            for (int j = 0; j < boundaryStep.getShapeLength(); j++) {
                inputPointList.add(new Point2D(boundaryStep.getXCoordinateAtIndex(j), boundaryStep.getYCoordinateAtIndex(j)));
            }
        }

        Point2D firstPoint = inputPointList.get(0);
        inputPointList.add(firstPoint);

        Point2D[] points = inputPointList.toArray(new Point2D[inputPointList.size()]);
        int n = points.length;

        for (int i = 0; i < n - 2; i++) {
            Pair<Point2D, Point2D> controlPoints = getControlPoints(points[i], points[i + 1], points[i + 2], bezierCurveSmoothness);
            cp.add(controlPoints.getKey());
            cp.add(controlPoints.getValue());
        }
        cp.add(cp.get(cp.size() - 2));
        cp.add(cp.get(cp.size() - 1));

        Point2D[] controlPoints = cp.toArray(new Point2D[cp.size()]);

        for (int j = 1; j < n - 1; j++) {
            if(j == 1){
                graphicsContext.moveTo(points[j].getX(), points[j].getY());
            }
            Point2D controlPoint1 = controlPoints[2 * j - 1];
            Point2D controlPoint2 = controlPoints[2 * j];

            graphicsContext.bezierCurveTo(controlPoint1.getX(), controlPoint1.getY(),
                    controlPoint2.getX(), controlPoint2.getY(),
                    points[j + 1].getX(), points[j + 1].getY());
        }
    }

    void drawSpline(GraphicsContext ctx, List<Point2D[]> input, double t) {
        List<Point2D> cp = new ArrayList<>();   // array of control points, as x0,y0,x1,y1,...

        List<Point2D> inputPointList = new ArrayList<>();
        Point2D[] lastBoundaryShape = input.get(input.size() - 1);
        Point2D lastPoint = lastBoundaryShape[lastBoundaryShape.length - 1];
        inputPointList.add(lastPoint);

        for (int i = 0; i < input.size(); i++) {
            Point2D[] point2Ds = input.get(i);
            for (int j = 0; j < point2Ds.length; j++) {
                inputPointList.add(point2Ds[j]);
            }
        }

        Point2D firstPoint = inputPointList.get(0);
        inputPointList.add(firstPoint);

        Point2D[] points = inputPointList.toArray(new Point2D[inputPointList.size()]);
        int n = points.length;

        for (int i = 0; i < n - 2; i++) {
            Pair<Point2D, Point2D> controlPoints = getControlPoints(points[i], points[i + 1], points[i + 2], t);
            cp.add(controlPoints.getKey());
            cp.add(controlPoints.getValue());
        }
        cp.add(cp.get(cp.size() - 2));
        cp.add(cp.get(cp.size() - 1));

        Point2D[] controlPoints = cp.toArray(new Point2D[cp.size()]);

        for (int j = 1; j < n - 1; j++) {
            if(j == 1){
                ctx.moveTo(points[j].getX(), points[j].getY());
            }
            Point2D controlPoint1 = controlPoints[2 * j - 1];
            Point2D controlPoint2 = controlPoints[2 * j];

            ctx.bezierCurveTo(controlPoint1.getX(), controlPoint1.getY(),
                    controlPoint2.getX(), controlPoint2.getY(),
                    points[j + 1].getX(), points[j + 1].getY());
        }
    }

    private Pair<Point2D, Point2D> getControlPoints(Point2D x0, Point2D x1, Point2D x2, double t) {
        //  x0,y0,x1,y1 are the coordinates of the end (knot) pts of this segment
        //  x2,y2 is the next knot -- not connected here but needed to calculate p2
        //  p1 is the control point calculated here, from x1 back toward x0.
        //  p2 is the next control point, calculated here and returned to become the
        //  next segment's p1.
        //  t is the 'tension' which controls how far the control points spread.

        //  Scaling factors: distances from this knot to the previous and following knots.
        double d01 = Math.sqrt(Math.pow(x1.getX() - x0.getX(), 2) + Math.pow(x1.getY() - x0.getY(), 2));
        double d12 = Math.sqrt(Math.pow(x2.getX() - x1.getX(), 2) + Math.pow(x2.getY() - x1.getY(), 2));

        double fa = t * d01 / (d01 + d12);
        double fb = t - fa;

        double p1x = x1.getX() + fa * (x0.getX() - x2.getX());
        double p1y = x1.getY() + fa * (x0.getY() - x2.getY());

        double p2x = x1.getX() - fb * (x0.getX() - x2.getX());
        double p2y = x1.getY() - fb * (x0.getY() - x2.getY());

        return new Pair(new Point2D(p1x, p1y), new Point2D(p2x, p2y));
    }


    public void initForNextRenderingPhase() {
    }

    public void finishRenderingPhase() {
    }

}