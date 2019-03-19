package com.deskera.mock;

import com.deskera.mock.entities.Item;
import com.deskera.mock.entities.ItemDao;
import com.deskera.mock.entities.Table;
import com.deskera.mock.entities.TableDao;
import com.deskera.mock.viewModels.ItemsViewModel;
import com.deskera.mock.viewModels.TablesViewModel;
import com.deskera.mock.views.fragments.TableFragment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DatabaseTest {
    @Mock
    private TableDao tableDao;

    @Mock
    private ItemDao itemDao;

    @InjectMocks
    private TablesViewModel tablesViewModel;


    @InjectMocks
    private ItemsViewModel itemsViewModel;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertTable() {

        when(tableDao.insert(any(Table.class))).thenReturn(567L);
        Table table = new Table();
        assertThat(tablesViewModel.insertTable(table), is(notNullValue()));
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
