// Prevayler(TM) - The Open-Source Prevalence Layer.
// Copyright (C) 2001-2003 Klaus Wuestefeld.
// This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License version 2.1 as published by the Free Software Foundation. This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details. You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.

package org.prevayler.implementation;

import org.prevayler.Clock;
import org.prevayler.Prevayler;
import org.prevayler.Transaction;


public class TransientPrevayler implements Prevayler {

	private final Object _prevalentSystem;
	private final Clock _clock;


	public TransientPrevayler(Object prevalentSystem, Clock clock) {
		_prevalentSystem = prevalentSystem;
		_clock = clock;
	}

	public Object prevalentSystem() {
		return _prevalentSystem;
	}

	public Clock clock() {
		return _clock;
	}

	public void execute(Transaction transaction) {
		synchronized (_prevalentSystem)	{
			transaction.executeOn(_prevalentSystem, _clock.time());
		}
	}

}
