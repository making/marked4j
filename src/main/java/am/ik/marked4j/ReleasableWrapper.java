/*
 * Copyright (C) 2014-2019 Toshiaki Maki <makingx@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package am.ik.marked4j;

import com.eclipsesource.v8.Releasable;

import java.io.Closeable;

class ReleasableWrapper<T extends Releasable> implements Closeable {

    private final T target;

    ReleasableWrapper(T target) {
        this.target = target;
    }

    final T unwrap() {
        return this.target;
    }

    @Override
    public final void close() {
        this.target.release();
    }
}
