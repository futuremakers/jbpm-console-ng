/*
 * Copyright 2014 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.console.ng.mobile.pr.client.definition.details;

import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.RoundPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import java.util.Map;
import javax.inject.Inject;
import org.jbpm.console.ng.mobile.core.client.AbstractView;
import org.jbpm.console.ng.mobile.core.client.MGWTPlaceManager;
import org.jbpm.console.ng.pr.model.ProcessSummary;

/**
 *
 * @author livthomas
 */
public class ProcessDefinitionDetailsViewImpl extends AbstractView implements
        ProcessDefinitionDetailsPresenter.ProcessDefinitionDetailsView {

    private final MTextBox definitionIdText = new MTextBox();
    private final MTextBox definitionNameText = new MTextBox();
    private final MTextBox deploymentText = new MTextBox();

    private final Button newInstanceButton;

    private String processId;
    private String deploymentId;

    @Inject
    private MGWTPlaceManager placeManager;

    private ProcessDefinitionDetailsPresenter presenter;

    public ProcessDefinitionDetailsViewImpl() {
        title.setHTML("Definition Details");

        RoundPanel roundPanel = new RoundPanel();

        definitionIdText.setReadOnly(true);
        definitionNameText.setReadOnly(true);
        deploymentText.setReadOnly(true);

        WidgetList widgetList = new WidgetList();
        widgetList.setRound(true);
        widgetList.add(new FormListEntry("Definition Id", definitionIdText));
        widgetList.add(new FormListEntry("Definition Name", definitionNameText));
        widgetList.add(new FormListEntry("Deployment", deploymentText));
        roundPanel.add(widgetList);

        newInstanceButton = new Button("New Instance");
        roundPanel.add(newInstanceButton);

        layoutPanel.add(roundPanel);
    }

    @Override
    public void init(final ProcessDefinitionDetailsPresenter presenter) {
        this.presenter = presenter;

        headerBackButton.addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                placeManager.goTo("Process Definitions List", Animation.SLIDE_REVERSE);
            }
        });

        newInstanceButton.addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                presenter.startProcess(deploymentId, processId);
            }
        });
    }

    @Override
    public void refreshDetails(ProcessSummary process) {
        definitionIdText.setText(process.getId());
        definitionNameText.setText(process.getName());
        deploymentText.setText(process.getDeploymentId());
    }

    @Override
    public void refresh() {
        presenter.refresh(deploymentId, processId);
    }

    @Override
    public void setParameters(Map<String, Object> params) {
        processId = (String) params.get("processId");
        deploymentId = (String) params.get("deploymentId");
    }

}