package cool.arch.stateroom;

/*
 * #%L cool.arch.stateroom:stateroom %% Copyright (C) 2015 CoolArch %% Licensed to the Apache
 * Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License.
 * #L%
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Log {

	static {
		try (final InputStream inputStream = Log.class.getResourceAsStream("/logging.properties")) {
			LogManager.getLogManager()
				.readConfiguration(inputStream);
		} catch (final IOException e) {
			Logger.getAnonymousLogger()
				.severe("Could not load default logging.properties file");
			Logger.getAnonymousLogger()
				.severe(e.getMessage());
		}
	}

	public static Logger getLogger(final String name) {
		return Logger.getLogger(name);
	}
}
