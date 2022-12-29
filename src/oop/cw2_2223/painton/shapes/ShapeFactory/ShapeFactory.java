package oop.cw2_2223.painton.shapes.ShapeFactory;

import oop.cw2_2223.painton.ImagePane;
import oop.cw2_2223.painton.shapes.PaintableShape;
import oop.cw2_2223.painton.shapes.compound.BoundedOval;
import oop.cw2_2223.painton.shapes.compound.BoundedRectangle;
import oop.cw2_2223.painton.shapes.compound.TextOval;
import oop.cw2_2223.painton.shapes.compound.TextRectangle;
import oop.cw2_2223.painton.shapes.simple.*;

import java.util.ArrayList;
import java.util.List;

public class ShapeFactory {
        ;
    private static PaintableShape newShape;
    private static List<String> menu = new ArrayList<>();

    public static PaintableShape createShapee(String shape, ImagePane image){
        switch (shape) {
            case "Filled_Oval":
                return newShape = new FilledOval(image);
            case "Image_Shape":
                return newShape = new ImageShape(image);
            case "Open_Oval":
                return newShape = new OpenOval(image);
            case "Text_Oval":
                return newShape = new TextOval(image);
            case "Text_Shape":
                return newShape = new TextShape(image);
            case "Bounded_Oval":
                return newShape = new BoundedOval(image);
            case "Filled_Rectangle":
                return newShape = new FilledRectangle(image);
            case  "Open_Rectangle":
                return newShape = new OpenRectangle(image);
            default:
                return null;
        }
    }
    public static List<String> getMenu() {
        menu.add("Filled_Oval");
        menu.add("Image_Shape");
        menu.add("Open_Oval");
        menu.add("Text_Oval");
        menu.add("Bounded_Oval");
        menu.add("Filled_Rectangle");
        menu.add("Open_Rectangle");
        return menu;
    }






    public enum ShapeType {
        Filled_Oval, Image_Shape, Open_Oval, Text_Shape, Bounded_Oval, Text_Oval,
        Filled_Rectangle, Open_Rectangle, Text_Rectangle, Bounded_Rectangle
    }
    public static PaintableShape createShape(ShapeType shape, ImagePane image) {
        return switch (shape) {
            case Filled_Oval -> newShape = new FilledOval(image);
            case Image_Shape -> newShape = new ImageShape(image);
            case Open_Oval -> newShape = new OpenOval(image);
            case Text_Oval -> newShape = new TextOval(image);
            case Text_Shape -> newShape = new TextShape(image);
            case Bounded_Oval -> newShape = new BoundedOval(image);
            case Filled_Rectangle -> newShape = new FilledRectangle(image);
            case Open_Rectangle -> newShape = new OpenRectangle(image);
            case Text_Rectangle -> newShape = new TextRectangle(image);
            case Bounded_Rectangle -> newShape = new BoundedRectangle(image);
            default -> null;
        };
    }
}
