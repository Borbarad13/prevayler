// Prevayler(TM) - The Open-Source Prevalence Layer.
// Copyright (C) 2001-2003 Klaus Wuestefeld.
// This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License version 2.1 as published by the Free Software Foundation. This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details. You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.

package org.prevayler;

import java.util.Date;

/**
 * An atomic transaction to be executed on a prevalent system. Any operation which changes the observable state of a prevalent system must be encapsulated as a Transaction. <br><br> IMPORTANT: Transactions CANNOT reference business objects directly. Instead, they must search the business objects they need given the Prevalent System. See org.prevayler.demos for usage examples. <br><br> Business objects referenced in a transaction will be mere copies of the original business objects when that transaction is recovered from the serialized transactionLog file. This will make the transactions work when they are executed for the first time but have no effect during shutdown recovery. This is known as the prevalence baptism problem because everyone comes across it, despite of this warning. 
 */
public interface Transaction extends java.io.Serializable {

	/**
	 * This method is called by Prevayler.execute(Transaction) to execute this transaction on the given prevalent system. See org.prevayler.demos for usage examples.
	 * @param prevalentSystem The system on which this transaction will execute.
	 * @param executionTime The time at which this transaction is being executed. Every transaction executes completely within a single moment in time. Logically, a prevalent system's time does not pass during the execution of a transaction.
	 */
	public void executeOn(Object prevalentSystem, Date executionTime);

}
