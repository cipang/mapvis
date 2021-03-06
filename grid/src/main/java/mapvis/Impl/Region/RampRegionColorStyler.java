package mapvis.Impl.Region;

import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;
import mapvis.Impl.Tile.TileStylerBase;
import mapvis.common.datatype.Tree2;
import mapvis.models.Region;

import java.util.List;

/**
 * Created by dacc on 11/13/2015.
 * RampRegionColor styler assigns the color of each Region with respect
 * to the depth/level of each node in the tree. For higher levels/depth
 * values the color of the region becomes darker.
 */
public class RampRegionColorStyler<T> extends RegionStylerBase<T> {
    public int depth;
    private ObjectProperty<Color> background;

    public RampRegionColorStyler(ObjectProperty<Tree2<T>> tree, TileStylerBase.StylerUIElements stylerUIElements) {
        super(tree, stylerUIElements);
        background = stylerUIElements.getBackground();
    }

    @Override
    public Color getColor(Region<T> region) {
        if(!isRegionVisible(region)){
            //calc the parent node corresponding to the max region level to show
            List<T> pathToNode = tree.get().getPathToNode(region.getNodeItem());
            int levelsToGoUp = region.getLevel() - maxRegionLevelToShow.intValue();
            int indexOfParentWithMaxLevelToShow = Math.max(0, pathToNode.size() - levelsToGoUp - 1);

            return getColorByValue(pathToNode.get(indexOfParentWithMaxLevelToShow));
        }
        return getColorByValue(region.getNodeItem());
    }

    @Override
    public Color getColorByValue(T node) {
        int level = maxRegionLevelToShow.intValue();

        double upper = (6 - level) * 100;
        double lower = 0;
        double x = tree.get().getWeight(node);
        x = Math.max(lower, x);
        x = Math.min(x, upper);

        x = (x - lower) / (upper - lower);

        Color color = Color.color(x, 0.0, 0.0);

        return color;
    }

    @Override
    public double getBorderWidthByLevel(int l) {
        return Math.pow(depth + 1 - l, 1.2)/2.0;
    }

    @Override
    public Color getBackground() {
        return background.get();
    }

    public ObjectProperty<Color> backgroundProperty() {
        return background;
    }

    public void setBackground(Color background) {
        this.background.set(background);
    }


}
