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
package org.eclipse.che.ide.editor.macro;

import com.google.common.annotations.Beta;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.api.promises.client.PromiseProvider;
import org.eclipse.che.ide.api.editor.EditorAgent;
import org.eclipse.che.ide.api.editor.EditorPartPresenter;

/**
 * Provider which is responsible for retrieving the file name from the opened editor.
 *
 * Macro provided: <code>${editor.current.file.name}</code>
 *
 * @see AbstractEditorMacroProvider
 * @see EditorAgent
 * @since 4.7.0
 */
@Beta
@Singleton
public class EditorCurrentFileNameProvider extends AbstractEditorMacroProvider {

    public static final String KEY = "${editor.current.file.name}";

    private PromiseProvider promises;

    @Inject
    public EditorCurrentFileNameProvider(EditorAgent editorAgent,
                                         PromiseProvider promises) {
        super(editorAgent);
        this.promises = promises;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return KEY;
    }

    @Override
    public String getDescription() {
        return "Currently selected file in editor";
    }

    /** {@inheritDoc} */
    @Override
    public Promise<String> expand() {
        final EditorPartPresenter editor = getActiveEditor();

        if (editor == null) {
            return promises.resolve("");
        }

        return promises.resolve(editor.getEditorInput().getFile().getName());
    }
}
