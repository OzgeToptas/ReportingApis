package com.ozge.util;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class BaseTestCase {

    @Before
    public void initMock() {
        MockitoAnnotations.initMocks(this);
    }

}
