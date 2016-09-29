/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.plugin.maven.client.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.ide.api.command.CommandPage;
import org.eclipse.che.ide.api.command.CommandType;
import org.eclipse.che.ide.api.icon.Icon;
import org.eclipse.che.ide.api.icon.IconRegistry;
import org.eclipse.che.ide.extension.machine.client.command.macros.CurrentProjectPathProvider;
import org.eclipse.che.ide.extension.machine.client.command.macros.CurrentProjectRelativePathProvider;
import org.eclipse.che.ide.extension.machine.client.command.macros.ServerPortProvider;
import org.eclipse.che.plugin.maven.client.MavenResources;

import java.util.LinkedList;
import java.util.List;

/**
 * Maven command type.
 *
 * @author Artem Zatsarynnyi
 */
@Singleton
public class MavenCommandType implements CommandType {

    private static final String ID               = "mvn";
    private static final String COMMAND_TEMPLATE = "mvn clean install";
    private static final String DEF_PORT         = "8080";

    private final MavenTestCommandProducer           mavenTestCommandProducer;
    private final CurrentProjectPathProvider         currentProjectPathProvider;
    private final CurrentProjectRelativePathProvider currentProjectRelativePathProvider;
    private final List<CommandPage>                  pages;

    @Inject
    public MavenCommandType(MavenResources resources,
                            MavenCommandPagePresenter page,
                            MavenTestCommandProducer mavenTestCommandProducer,
                            CurrentProjectPathProvider currentProjectPathProvider,
                            CurrentProjectRelativePathProvider currentProjectRelativePathProvider,
                            IconRegistry iconRegistry) {
        this.mavenTestCommandProducer = mavenTestCommandProducer;
        this.currentProjectPathProvider = currentProjectPathProvider;
        this.currentProjectRelativePathProvider = currentProjectRelativePathProvider;

        pages = new LinkedList<>();
        pages.add(page);

        iconRegistry.registerIcon(new Icon(ID + ".commands.category.icon", resources.maven()));
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getDisplayName() {
        return "Maven";
    }

    @Override
    public String getDescription() {
        return "Command for executing Maven command line";
    }

    @Override
    public List<CommandPage> getPages() {
        return pages;
    }

    @Override
    public String getCommandLineTemplate() {
        return COMMAND_TEMPLATE + " -f " + currentProjectPathProvider.getName();
    }

    @Override
    public String getPreviewUrlTemplate() {
        //TODO: hardcode http after switching WS Master to https
        return "http://" + ServerPortProvider.KEY_TEMPLATE.replace("%", DEF_PORT) + "/" +
               currentProjectRelativePathProvider.getName();
    }
}
