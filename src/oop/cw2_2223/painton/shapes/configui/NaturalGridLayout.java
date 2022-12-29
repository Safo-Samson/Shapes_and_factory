package oop.cw2_2223.painton.shapes.configui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * Layout manager that arranges components in a grid whose column widths and row
 * heights are determined by the preferred size of the components they contain.
 * The widest component in a column sets the width of the column and the tallest
 * component in a row sets the height of the row. Components added to the
 * container are added to the grid from left to right, top to bottom.
 * <p>
 * By default components fill their allocated cells, and the grid is stretched
 * in both dimensions if the container is resized, while keeping the same
 * proportion of column widths and row heights. Various parameters can adjust
 * this behaviour for both the components in their cells and the grid within the
 * container, individually in the horizontal and vertical dimensions.
 * <p>
 * A 'template' component can be specified by adding a component to the
 * container with the constraint object {@link #MINIMUM_ROW_HEIGHT_TEMPLATE}
 * using the {@link Container#add(Component, Object)} method. This
 * component then defines a minimum row height that other rows will be fitted
 * to. The main purpose of this is to allow forms with textfields in some rows
 * and comboboxes in other rows to be presented with rows of uniform height (add
 * one combobox with the constraint object).
 * <p>
 * This version adds the possibility of components that span multiple columns on
 * single row. To achieve this, wrap the component in an instance of
 * {@code NaturalGridLayout$MultiSpanPanel} before adding it to the layout. Note
 * that components that span more than one column are <i>disregarded</i> when
 * setting the widths of the columns. If a component asks to span more columns
 * than remain on the row it is on, it will only get to span to the end of the
 * row.
 *
 * @version Mike Child, 30/04/2019
 * @version Mike Child, 23/04/2021
 */
public class NaturalGridLayout implements LayoutManager {

  public enum Align {
    FILL, CENTER, LEADING, TRAILING
  };

  public static final String MINIMUM_ROW_HEIGHT_TEMPLATE = "MINIMUM_ROW_HEIGHT_TEMPLATE";

  private final int columns;
  private final int columnSpacing;
  private final int rowSpacing;

  private final Align componentAlignX;
  private final Align componentAlignY;

  private final boolean permitUndersizingX;
  private final boolean permitUndersizingY;

  private final Align gridAlignX;
  private final Align gridAlignY;

  private Component minimumRowHeightTemplateComponent;

  /**
   * Constructs a layout with the given number of columns and the following
   * properties:
   * <ul>
   * <li>There is no space between columns or rows.
   * <li>Components are allowed to be resized below their preferred size.
   * <li>Components fill their cells in both dimensions.
   * <li>The grid fills the container in both dimensions.
   * </ul>
   *
   * @param columns - number of columns in the grid
   */
  public NaturalGridLayout(final int columns) {
    this(columns, 0, 0);
  }

  /**
   * Constructs a layout with the given number of columns, column and row
   * spacings, and the following properties:
   * <ul>
   * <li>Components are allowed to be resized below their preferred size.
   * <li>Components fill their cells in both dimensions.
   * <li>The grid fills the container in both dimensions.
   * </ul>
   *
   * @param columns       - number of columns in the grid
   * @param columnSpacing - number of pixels of space between columns
   * @param rowSpacing    - number of pixels of space between rows
   */
  public NaturalGridLayout(final int columns, final int columnSpacing, final int rowSpacing) {
    this(columns, columnSpacing, rowSpacing, true, true);
  }

  /**
   * Constructs a layout with the given number of columns, ability to under-size
   * components, and the following properties: cells.
   * <ul>
   * <li>There is no space between columns or rows.
   * <li>Components fill their cells in both dimensions.
   * <li>The grid fills the container in both dimensions.
   * </ul>
   *
   * @param columns            - number of columns in the grid
   * @param permitUndersizingX - if true, allow components to be resized smaller
   *                           than their preferred width
   * @param permitUndersizingY - if true, allow components to be resized smaller
   *                           than their preferred height
   */
  public NaturalGridLayout(final int columns, final boolean permitUndersizingX, final boolean permitUndersizingY) {
    this(columns, 0, 0, permitUndersizingX, permitUndersizingY);
  }

  /**
   * Constructs a layout with the given number of columns, column and row
   * spacings, ability to under-size components and the following properties:
   * <ul>
   * <li>Components fill their cells in both dimensions.
   * <li>The grid fills the container in both dimensions.
   * </ul>
   *
   * @param columns            - number of columns in the grid
   * @param columnSpacing      - number of pixels of space between columns
   * @param rowSpacing         - number of pixels of space between rows
   * @param permitUndersizingX - if true, allow components to be resized smaller
   *                           than their preferred width
   * @param permitUndersizingY - if true, allow components to be resized smaller
   *                           than their preferred height
   */
  public NaturalGridLayout(final int columns, final int columnSpacing, final int rowSpacing,
      final boolean permitUndersizingX, final boolean permitUndersizingY) {
    this(columns, columnSpacing, rowSpacing, permitUndersizingX, permitUndersizingY, Align.FILL, Align.FILL);
  }

  /**
   * Constructs a layout with the given number of columns, ability to under-size
   * components, and the stated behaviour for components filling their cells.
   * <ul>
   * <li>There is no space between columns or rows.
   * <li>The grid fills the container in both dimensions.
   * </ul>
   *
   * @param columns            - number of columns in the grid
   * @param permitUndersizingX - if true, allow components to be resized smaller
   *                           than their preferred width
   * @param permitUndersizingY - if true, allow components to be resized smaller
   *                           than their preferred height
   * @param componentAlignX    - control whether components fill their cell's
   *                           width or else where they are placed within it
   * @param componentAlignY    - control whether components fill their cell's
   *                           height or else where they are placed within it
   */
  public NaturalGridLayout(final int columns, final boolean permitUndersizingX, final boolean permitUndersizingY,
      final Align componentAlignX, final Align componentAlignY) {
    this(columns, 0, 0, permitUndersizingX, permitUndersizingY, componentAlignX, componentAlignY);
  }

  /**
   * Constructs a layout with the given number of columns, column and row
   * spacings, ability to under-size components, and the stated behaviour for
   * components filling their cells.
   * <ul>
   * <li>The grid fills the container in both dimensions.
   * </ul>
   *
   * @param columns            - number of columns in the grid
   * @param columnSpacing      - number of pixels of space between columns
   * @param rowSpacing         - number of pixels of space between rows
   * @param permitUndersizingX - if true, allow components to be resized smaller
   *                           than their preferred width
   * @param permitUndersizingY - if true, allow components to be resized smaller
   *                           than their preferred height
   * @param componentAlignX    - control whether components fill their cell's
   *                           width or else where they are placed within it
   * @param componentAlignY    - control whether components fill their cell's
   *                           height or else where they are placed within it
   */
  public NaturalGridLayout(final int columns, final int columnSpacing, final int rowSpacing,
      final boolean permitUndersizingX, final boolean permitUndersizingY, final Align componentAlignX,
      final Align componentAlignY) {
    this(columns, columnSpacing, rowSpacing, permitUndersizingX, permitUndersizingY, componentAlignX, componentAlignY,
        Align.FILL, Align.FILL);
  }

  /**
   *
   * @param columns            - number of columns in the grid
   * @param columnSpacing      - number of pixels of space between columns
   * @param rowSpacing         - number of pixels of space between rows
   * @param permitUndersizingX - if true, allow components to be resized smaller
   *                           than their preferred width
   * @param permitUndersizingY - if true, allow components to be resized smaller
   *                           than their preferred height
   * @param componentAlignX    - control whether components fill their cell's
   *                           width or else where they are placed within it
   * @param componentAlignY    - control whether components fill their cell's
   *                           height or else where they are placed within it
   * @param gridAlignX         - control whether the entire grid fill's the
   *                           container's width or else where it is placed within
   *                           it
   * @param gridAlignY         - control whether the entire grid fill's the
   *                           container's height or else where it is placed
   *                           within it
   */
  public NaturalGridLayout(final int columns, final int columnSpacing, final int rowSpacing,
      final boolean permitUndersizingX, final boolean permitUndersizingY, final Align componentAlignX,
      final Align componentAlignY, final Align gridAlignX, final Align gridAlignY) {
    this.columns = columns;
    this.componentAlignX = componentAlignX;
    this.componentAlignY = componentAlignY;
    this.columnSpacing = columnSpacing;
    this.rowSpacing = rowSpacing;
    this.permitUndersizingX = permitUndersizingX;
    this.permitUndersizingY = permitUndersizingY;
    this.gridAlignX = gridAlignX;
    this.gridAlignY = gridAlignY;
  }

  @Override
  public void addLayoutComponent(final String string, final Component component) {
    if (string == MINIMUM_ROW_HEIGHT_TEMPLATE) {
      this.minimumRowHeightTemplateComponent = component;
    }
  }

  @Override
  public void removeLayoutComponent(final Component component) {
    if (component == this.minimumRowHeightTemplateComponent) {
      this.minimumRowHeightTemplateComponent = null;
    }
  }

  @Override
  public void layoutContainer(final Container container) {

    final int numberOfCells = calculateNumberOfCells(container);

    final int[] columnWidths = new int[this.columns];

    int numberOfRows = numberOfCells / this.columns;
    if (numberOfCells % this.columns != 0) {
      numberOfRows++;
    }
    final int[] rowHeights = new int[numberOfRows];

    computeColumnWidthsAndRowHeights(container, numberOfCells, columnWidths, rowHeights, true);

    final Insets insets = container.getInsets();
    final int containerInnerWidth = container.getWidth() - (insets.left + insets.right);
    final int containerInnerHeight = container.getHeight() - (insets.top + insets.bottom);
    final int effectiveInnerWidth = containerInnerWidth - (this.columnSpacing * (columnWidths.length - 1));
    final int effectiveInnerHeight = containerInnerHeight - (this.rowSpacing * (rowHeights.length - 1));

    final int sumOfColumnWidths = sumOf(columnWidths);
    final int sumOfRowHeights = sumOf(rowHeights);

    int initialCx = insets.left;

    if (this.gridAlignX == Align.FILL) {
      // grid will fill width
      if (this.permitUndersizingX || (effectiveInnerWidth >= sumOfColumnWidths)) {
        proportionArray(columnWidths, effectiveInnerWidth);
      }
    } else {
      // grid maybe centred, left or right aligned if there is space
      final int spaceX = effectiveInnerWidth - sumOfColumnWidths;
      if (spaceX > 0) {
        if (this.gridAlignX == Align.CENTER) {
          initialCx += spaceX / 2;
        } else if (this.gridAlignX == Align.TRAILING) {
          initialCx += spaceX;
        }
      }
    }

    int initialRy = insets.top;

    if (this.gridAlignY == Align.FILL) {
      // grid will fill height
      if (this.permitUndersizingY || (effectiveInnerHeight >= sumOfRowHeights)) {
        proportionArray(rowHeights, effectiveInnerHeight);
      }
    } else {
      // grid maybe centred, top or bottom aligned if there is space
      final int spaceY = effectiveInnerHeight - sumOfRowHeights;
      if (spaceY > 0) {
        if (this.gridAlignY == Align.CENTER) {
          initialRy += spaceY / 2;
        } else if (this.gridAlignY == Align.TRAILING) {
          initialRy += spaceY;
        }
      }
    }

    int cx = initialCx;
    int ry = initialRy;

    int componentIndex = 0;
    int span = 1;
    for (int i = 0; i < numberOfCells; i += span) {
      final int column = i % this.columns;
      final int row = i / this.columns;
      final Component component = container.getComponent(componentIndex++);
      span = (component instanceof MultiSpanComponent) ? ((MultiSpanComponent) component).columnsToSpan() : 1;
      if (span < 1) {
        span = 1;
      } // guard against bad values
      final int remainingOnThisRow = this.columns - (i % this.columns);
      span = Math.min(span, remainingOnThisRow);

      int spanWidth = 0;
      for (int j = column; j < column + span; j++) {
        spanWidth += columnWidths[j];
      }
      spanWidth += this.columnSpacing * (span - 1);

      final Dimension preferred = component.getPreferredSize();

      int x = cx;
      int y = ry;
      int width = spanWidth;
      int height = rowHeights[row];

      if (this.componentAlignX != Align.FILL) {
        final int space = width - preferred.width;
        // if the component preferred width is less than the allocated width,
        // keep it and centre it in the allocated width (otherwise, use the allocated
        // width)
        if (space > 0) {
          if (this.componentAlignX == Align.CENTER) {
            // centre
            x += space / 2;
          } else if (this.componentAlignX == Align.TRAILING) {
            // right
            x += space;
          }
          width = preferred.width;
        }
      }

      if (this.componentAlignY != Align.FILL) {
        final int space = height - preferred.height;
        // if the component height is less than the allocated height,
        // keep it and centre it in the allocated height (otherwise, use the allocated
        // height)
        if (space > 0) {
          if (this.componentAlignY == Align.CENTER) {
            // centre
            y += space / 2;
          } else if (this.componentAlignY == Align.TRAILING) {
            // bottom
            y += space;
          }
          height = preferred.height;
        }
      }

      component.setBounds(x, y, width, height);

      if (column + span == (this.columns)) {
        cx = initialCx;
        ry += rowHeights[row] + this.rowSpacing;
      } else {
        cx += spanWidth + this.columnSpacing;
      }

      if (componentIndex >= container.getComponentCount()) {
        break;
      }

    }

  }

  @Override
  public Dimension minimumLayoutSize(final Container container) {
    return computeLayoutSize(container, false);
  }

  @Override
  public Dimension preferredLayoutSize(final Container container) {
    return computeLayoutSize(container, true);
  }

  private int calculateNumberOfCells(final Container container) {
    int numberOfCells = 0;
    for (final Component component : container.getComponents()) {
      if (component instanceof MultiSpanComponent) {
        int span = ((MultiSpanComponent) component).columnsToSpan();
        if (span < 1) {
          span = 1;
        } // guard against bad values
        final int remainingOnThisRow = this.columns - (numberOfCells % this.columns);
        span = Math.min(span, remainingOnThisRow);
        numberOfCells += span;
      } else {
        numberOfCells++;
      }
    }
    return numberOfCells;
  }

  private void computeColumnWidthsAndRowHeights(final Container container, final int numberOfCells,
      final int[] columnWidths, final int[] rowHeights, final boolean preferredSize) {
    int minimumRowHeight = -1;
    if (this.minimumRowHeightTemplateComponent != null) {
      final Dimension dimension = preferredSize ? this.minimumRowHeightTemplateComponent.getPreferredSize()
          : this.minimumRowHeightTemplateComponent.getMinimumSize();
      minimumRowHeight = dimension.height;
    }
    int componentIndex = 0;
    int span = 1;
    for (int i = 0; i < numberOfCells; i += span) {
      final int column = i % this.columns;
      final int row = i / this.columns;
      final Component component = container.getComponent(componentIndex++);
      span = (component instanceof MultiSpanComponent) ? ((MultiSpanComponent) component).columnsToSpan() : 1;
      if (span < 1) {
        span = 1;
      } // guard against bad values
      final int remainingOnThisRow = this.columns - (i % this.columns);
      span = Math.min(span, remainingOnThisRow);

      final Dimension dimension = preferredSize ? component.getPreferredSize() : component.getMinimumSize();

      // increase height of rows that are narrower than the minimum set by a template
      // component (if any).
      if (dimension.height < minimumRowHeight) {
        dimension.height = minimumRowHeight;
      }

      if ((span == 1) && (dimension.width > columnWidths[column])) {
        columnWidths[column] = dimension.width;
      }
      if (dimension.height > rowHeights[row]) {
        rowHeights[row] = dimension.height;
      }

      if (componentIndex >= container.getComponentCount()) {
        break;
      }
    }
  }

  /**
   * Increase the size of each element of the array until the sum of the array
   * equals availablePixels, while maintaining the relative sizes of the elements.
   */
  private void proportionArray(final int[] array, final int availablePixels) {
    int sum = 0;
    for (final int d : array) {
      sum += d;
    }
    int pixelsRemaining = availablePixels;
    final double[] remainderSpace = new double[array.length];
    for (int i = 0; i < array.length; i++) {
      final int m = availablePixels * array[i];
      remainderSpace[i] = m % sum; // decimal part of the division only
      array[i] = m / sum;
      pixelsRemaining -= array[i];
    }
    while (pixelsRemaining > 0) {
      final int mi = indexOfMax(remainderSpace);
      if (!Double.isNaN(mi)) {
        array[mi]++;
        pixelsRemaining--;
      } else {
        array[0] += pixelsRemaining;
        pixelsRemaining = 0;
      }
    }
  }

  /**
   * Compute the size this layout manager requires to display its contents, based
   * on either the preferred or minimum sizes of the components it contains
   * (controlled by preferredSize).
   */
  private Dimension computeLayoutSize(final Container container, final boolean preferredSize) {
    final int numberOfCells = calculateNumberOfCells(container);

    final int[] columnWidths = new int[this.columns];

    int numberOfRows = numberOfCells / this.columns;
    if (numberOfCells % this.columns != 0) {
      numberOfRows++;
    }
    final int[] rowHeights = new int[numberOfRows];

    computeColumnWidthsAndRowHeights(container, numberOfCells, columnWidths, rowHeights, preferredSize);

    final Dimension size = new Dimension(this.columnSpacing * (columnWidths.length - 1),
        this.rowSpacing * (rowHeights.length - 1));
    for (final int cw : columnWidths) {
      size.width += cw;
    }
    for (final int rh : rowHeights) {
      size.height += rh;
    }

    final Insets insets = container.getInsets();
    size.width += insets.left + insets.right;
    size.height += insets.top + insets.bottom;

    return size;
  }

  /**
   * Interface components to be laid out by this layout manager can implement if
   * they wish to span more than one column.
   */
  public static interface MultiSpanComponent {
    /**
     * Number of columns component wants to span. This is truncated to the number of
     * columns remaining on the row the component is located to if it is greater.
     */
    int columnsToSpan();
  }

  /**
   * Class implementing MultiSpanComponent to facilitate adding regular components
   * to the layout wrapped in this multispan component.
   */
  public static class MultiSpanJPanel extends JPanel implements MultiSpanComponent {
    private static final long serialVersionUID = 1L;

    private final int columnsToSpan;

    /**
     * Create a MultiSpanJPanel for the given number of columns with a BorderLayout
     * and no components inside it.
     * 
     * @param columnsToSpan - desired span greater than or equal to 1
     */
    public MultiSpanJPanel(final int columnsToSpan) {
      this(new BorderLayout(), columnsToSpan);
    }

    /**
     * Create a MultiSpanJPanel for the given number of columns with a BorderLayout
     * and add the given component to it in the centre region.
     * 
     * @param columnsToSpan - desired span greater than or equal to 1
     */
    public MultiSpanJPanel(final Component component, final int columnsToSpan) {
      this(new BorderLayout(), columnsToSpan);
      add(component);
    }

    /**
     * Create a MultiSpanJPanel for the given number of columns with a the given
     * layout manager and no components inside it.
     * 
     * @param columnsToSpan - desired span greater than or equal to 1
     */
    public MultiSpanJPanel(final LayoutManager layout, final int columnsToSpan) {
      super(layout);
      if (columnsToSpan < 1) {
        throw new IllegalArgumentException("Columns to span must be at least 1: " + columnsToSpan);
      }
      this.columnsToSpan = columnsToSpan;
    }

    @Override
    public int columnsToSpan() {
      return this.columnsToSpan;
    }

  }
  /**
   * Returns the sum of all elements of the array.
   */
  public static int sumOf( int[] array) {
    int sum = 0;
    for( int i = 0; i < array.length; i++) {
      sum += array[i];
    }
    return sum;
  }
  
  /**
   * Returns the index of the maximum value contained in array, returning the 
   * first occurrence of equal maximum values. Elements that contain NaN values
   * are ignored, and if the array only contains NaN values, -1 is returned.
   * 
   * @param array
   * @return index of the maximum value or -1 if all elements are NaN
   */
  public static int indexOfMax( double[] array) {
    double max = array[0];
    int index = Double.isNaN( max) ? -1 : 0;
    for( int i = 0; i < array.length; i++) {
      if ( !Double.isNaN( array[i])) {
        if ( Double.isNaN(max) || array[i] > max) {
          max = array[i];
          index = i;
        }
      }
    } 
    return index;
  }
}
