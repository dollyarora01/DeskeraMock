package com.deskera.mock;

import com.deskera.mock.entities.Item;
import com.deskera.mock.entities.ItemDao;
import com.deskera.mock.entities.Settings;
import com.deskera.mock.entities.Table;
import com.deskera.mock.entities.TableDao;
import com.deskera.mock.entities.User;
import com.deskera.mock.viewModels.ItemsViewModel;
import com.deskera.mock.viewModels.TablesViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class DatabaseTest {
    @Mock
    private ItemDao itemDao;
    @Mock
    private TableDao tableDao;

    @InjectMocks
    private TablesViewModel tablesViewModel;


    @InjectMocks
    private ItemsViewModel itemsViewModel;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void greenProbationDuration() {
        User user = new User();
        user.setUserId(1L);
        user.setSettings(new Settings(1L, false,
                null, 32.8, false, 1521490265000L, 1553026265000L));
        when(user.getSettings().getProbationDuration()).thenReturn("12 months 0 days");

        User newUser = new User();
        user.setUserId(2L);
        newUser.setSettings(new Settings(2L, false,
                null, 32.8, false, 1521490265000L, 1553026265000L));
        String probationLength = newUser.getSettings().getProbationDuration();
        assertThat(probationLength, is(notNullValue()));
        assertThat(probationLength, is("12 months 0 days"));
    }


    @Test
    public void greenInsertTable() {
        when(tableDao.insert(any(Table.class))).thenReturn(567L);
        Table table = new Table(0L, 1L, "Table mock name");
        Long tableId = tablesViewModel.insertTable(table);
        assertThat(tableId, is(567L));
    }

    @Test
    public void redInsertTable() {
        when(tableDao.insert(any(Table.class))).thenReturn(567L);
        assertThat(tablesViewModel.insertTable(null), is(0L));
    }

    @Test
    public void greenDeleteTable() {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                Table table = (Table) arguments[0];
                tableDao.delete(table);
                return null;
            }
        }).when(tableDao).delete(any(Table.class));


        boolean hasDeleted = tablesViewModel.deleteTable(new Table(1L, 1L, "Table Delete Mock"));
        assertThat(hasDeleted, is(true));
    }

    @Test
    public void redDeleteTable() {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                return null;
            }
        }).when(tableDao).delete(any(Table.class));


        boolean hasDeleted = tablesViewModel.deleteTable(new Table(1L, 1L, "Table Delete Mock"));

        assertThat(hasDeleted, is(notNullValue()));
        assertThat(hasDeleted, is(true));
    }

    @Test
    public void greenItemUpdate() {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length > 0 && arguments[0] != null) {

                    Item item = (Item) arguments[0];
                    item.setIsFavourite(true);
                }
                return null;
            }
        }).when(itemDao).update(any(Item.class));


        boolean hasUpdated = itemsViewModel.updateItem(new Item(1L, 1L, "Item Mock Description", true, "Item Mock Name",
                2L));

        assertThat(hasUpdated, is(notNullValue()));
        assertThat(hasUpdated, is(true));
    }

    @Test
    public void redItemUpdate() {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length > 0 && arguments[0] != null) {

                    Item item = (Item) arguments[0];
                    item.setIsFavourite(true);
                }
                return null;
            }
        }).when(itemDao).update(any(Item.class));


        boolean hasUpdated = itemsViewModel.updateItem(null);

        assertThat(hasUpdated, is(notNullValue()));
        assertThat(hasUpdated, is(false));
    }


}
