package client.gui.simplify;

import javafx.scene.control.TableColumn;
import server.spacemarine.SpaceMarine;

import java.util.Comparator;
import java.util.List;

public class TableColumnComparator<SpaceMarine> implements Comparator<SpaceMarine> {
    private final List<TableColumn<SpaceMarine, ?>> allColumns;

    public TableColumnComparator(final List<TableColumn<SpaceMarine, ?>> allColumns) {
        this.allColumns = allColumns;
    }

    @Override
    public int compare(final SpaceMarine o1, final SpaceMarine o2) {
        for (final TableColumn<SpaceMarine, ?> tc : allColumns) {
            if (!isSortable(tc)) {
                continue;
            }
            final Object value1 = tc.getCellData(o1);
            final Object value2 = tc.getCellData(o2);

            @SuppressWarnings("unchecked") final Comparator<Object> c = (Comparator<Object>) tc.getComparator();
            final int result = javafx.scene.control.TableColumn.SortType.ASCENDING.equals(tc.getSortType()) ? c.compare(value1, value2)
                    : c.compare(value2, value1);

            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    private boolean isSortable(final TableColumn<SpaceMarine, ?> tc) {
        return tc.getSortType() != null && tc.isSortable();
    }
}