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
package org.drools.workbench.screens.guided.dtable.client.widget.analysis.controller;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import org.drools.workbench.models.guided.dtable.shared.model.BaseColumn;

public class AfterColumnInserted
        extends GwtEvent<AfterColumnInserted.Handler> {

    public static final Type<Handler> TYPE = new Type<>();
    private final BaseColumn column;

    public AfterColumnInserted( final BaseColumn column ) {
        this.column = column;
    }

    public BaseColumn getColumn() {
        return column;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch( Handler handler ) {
        handler.onAfterColumnInserted( this );
    }

    public interface Handler
            extends
            EventHandler {

        void onAfterColumnInserted( AfterColumnInserted event );
    }

}
