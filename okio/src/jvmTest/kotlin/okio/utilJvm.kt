/*
 * Copyright (C) 2021 Square, Inc.
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
package okio

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import okio.Path.Companion.toOkioPath

@ExperimentalFileSystem
actual fun assertRelativeTo(
  a: Path,
  b: Path,
  bRelativeToA: Path,
  consistentWithJavaNioPath: Boolean,
) {
  assertEquals(bRelativeToA, b.relativeTo(a))
  // Also confirm our behavior is consistent with java.nio.
  if (consistentWithJavaNioPath) {
    assertEquals(bRelativeToA, a.toNioPath().relativize(b.toNioPath()).toOkioPath())
  }
}

@ExperimentalFileSystem
actual fun assertRelativeToFails(
  a: Path,
  b: Path,
  consistentWithJavaNioPath: Boolean,
): IllegalArgumentException {
  // Check java.nio first.
  if (consistentWithJavaNioPath) {
    assertFailsWith<IllegalArgumentException> {
      a.toNioPath().relativize(b.toNioPath())
    }
  }
  // Return okio.
  return assertFailsWith {
    b.relativeTo(a)
  }
}
