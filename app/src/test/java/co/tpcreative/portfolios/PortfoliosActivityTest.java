package co.tpcreative.portfolios;

import com.github.mikephil.charting.data.BarData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import co.tpcreative.portfolios.model.CObject;
import co.tpcreative.portfolios.ui.portfolios.activity.PortfoliosPresenter;
import co.tpcreative.portfolios.ui.portfolios.activity.PortfoliosView;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Phong on 5/28/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class PortfoliosActivityTest {

    @Mock
    private PortfoliosView view;
    private PortfoliosPresenter presenter ;


    @Before
    public void setUp() throws Exception {
        presenter = new PortfoliosPresenter();
    }

    @Test
    public void shouldAction() throws Exception {

    }

}
