/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.workbench.services.verifier.core.cache.inspectors.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.drools.workbench.services.verifier.api.client.checks.util.NullEqualityOperator;
import org.drools.workbench.services.verifier.api.client.configuration.AnalyzerConfiguration;
import org.drools.workbench.services.verifier.api.client.index.Column;
import org.drools.workbench.services.verifier.api.client.index.Field;
import org.drools.workbench.services.verifier.api.client.index.FieldCondition;
import org.drools.workbench.services.verifier.api.client.index.ObjectField;
import org.drools.workbench.services.verifier.api.client.index.keys.Values;
import org.drools.workbench.services.verifier.core.checks.AnalyzerConfigurationMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(Parameterized.class)
public class StringConditionInspectorToHumanReadableTest {

    private static final String FIELD_NAME = "name";
    private static final String VALUE = "someValue";

    private final String operator;

    private AnalyzerConfiguration configuration;

    public StringConditionInspectorToHumanReadableTest( String operator ) {
        this.operator = operator;
    }

    @Parameters
    public static Collection<Object[]> testData() {
        // not sure if '== null' and '!= null' from OperatorsOracle.STRING_OPERATORS make much sense here
        ArrayList<Object> data = new ArrayList<Object>( Arrays.asList( new String[]{"==", "!=", "<", ">", "<=", ">=", "matches", "soundslike", "== null", "!= null"} ) );
        data.addAll( Arrays.asList( new String[]{"in", "not in"} ) );
        Collection<Object[]> data2 = new ArrayList<Object[]>();
        for ( Object operator : data ) {
            data2.add( new Object[]{operator} );
        }
        return data2;
    }

    @Before
    public void setUp() throws
                        Exception {
        configuration = new AnalyzerConfigurationMock();

    }

    @Test
    public void testToHumanReadableString() {


        final StringConditionInspector inspector = getStringConditionInspector();

        if ( NullEqualityOperator.IS_NOT_NULL.matches( operator ) ) {
            assertEquals( format( "%s %s",
                                  FIELD_NAME,
                                  operator ),
                          inspector.toHumanReadableString() );
        } else if ( NullEqualityOperator.IS_NULL.matches( operator ) ) {
            assertEquals( format( "%s %s",
                                  FIELD_NAME,
                                  operator ),
                          inspector.toHumanReadableString() );
        } else {
            assertEquals( format( "%s %s %s",
                                  FIELD_NAME,
                                  operator,
                                  VALUE ),
                          inspector.toHumanReadableString() );
        }
    }

    private StringConditionInspector getStringConditionInspector() {
        if ( NullEqualityOperator.IS_NOT_NULL.matches( operator )  ) {
            return new StringConditionInspector( new FieldCondition<>( new Field( mock( ObjectField.class ),
                                                                                  "Test",
                                                                                  "String",
                                                                                  FIELD_NAME,
                                                                                  configuration ),
                                                                       mock( Column.class ),
                                                                       "!=",
                                                                       Values.nullValue(),
                                                                       configuration
            ),
                                                 configuration );
        } else if ( NullEqualityOperator.IS_NULL.matches(  operator ) ) {
            return new StringConditionInspector( new FieldCondition<>( new Field( mock( ObjectField.class ),
                                                                                  "Test",
                                                                                  "String",
                                                                                  FIELD_NAME,
                                                                                  configuration ),
                                                                       mock( Column.class ),
                                                                       "==",
                                                                       Values.nullValue(),
                                                                       configuration ),
                                                 configuration );
        } else {
            return new StringConditionInspector( new FieldCondition<>( new Field( mock( ObjectField.class ),
                                                                                  "Test",
                                                                                  "String",
                                                                                  FIELD_NAME,
                                                                                  configuration ),
                                                                       mock( Column.class ),
                                                                       operator,
                                                                       new Values( VALUE ),
                                                                       configuration ),
                                                 configuration );
        }
    }
}