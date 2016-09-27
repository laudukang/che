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
package org.eclipse.che.ide.command.macros;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;

import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.api.promises.client.js.Promises;
import org.eclipse.che.ide.api.command.macros.CommandPropertyValueProvider;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Custom macro provider which allows to register user's macro with the provided value.
 *
 * @author Vlad Zhukovskyi
 * @see CommandPropertyValueProvider
 * @since 4.7.0
 */
@Beta
public class CustomCommandPropertyValueProvider implements CommandPropertyValueProvider {

    private final String key;
    private final String value;
    private final String description;

    public CustomCommandPropertyValueProvider(String key, String value, String description) {
        this.key = checkNotNull(key, "Key should not be null");
        this.value = checkNotNull(value, "Value should not be null");
        this.description = checkNotNull(description, "Description should not be null");
    }

    /** {@inheritDoc} */
    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /** {@inheritDoc} */
    @Override
    public Promise<String> getValue() {
        return Promises.resolve(value);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomCommandPropertyValueProvider that = (CustomCommandPropertyValueProvider)o;
        return Objects.equal(key, that.key) &&
               Objects.equal(value, that.value) &&
               Objects.equal(description, that.description);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hashCode(key, value, description);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "CustomCommandPropertyValueProvider{" +
               "key='" + key + '\'' +
               ", value='" + value + '\'' +
               ", description='" + description + '\'' +
               '}';
    }
}