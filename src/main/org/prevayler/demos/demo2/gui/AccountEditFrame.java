package org.prevayler.demos.demo2.gui;

import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.prevayler.util.QueryExecuter;
import org.prevayler.demos.demo2.business.Account;
import org.prevayler.demos.demo2.business.AccountListener;
import org.prevayler.demos.demo2.business.transactions.Deposit;
import org.prevayler.demos.demo2.business.transactions.HolderChange;
import org.prevayler.demos.demo2.business.transactions.Withdrawal;

class AccountEditFrame extends AccountFrame implements AccountListener {
	
	private final Account account;
	private JTextField balanceField;
	private JList historyList;
	
	AccountEditFrame(Account account, QueryExecuter prevayler, Container container) {
		super("Account " + account.numberString(), prevayler, container);

		this.account = account;
		account.addAccountListener(this);
		accountChanged();
		
		holderField.addFocusListener(new HolderListener());
		
        setBounds(50,50,306,300);
	}

	protected void addFields(Box fieldBox) {
		super.addFields(fieldBox);
		
		fieldBox.add(gap());
		fieldBox.add(labelContainer("Transaction History"));
		historyList = new JList();
		historyList.setEnabled(false);
		fieldBox.add(new JScrollPane(historyList));
		
		fieldBox.add(gap());
		fieldBox.add(labelContainer("Balance"));
		balanceField = new JTextField();
		balanceField.setEnabled(false);
		fieldBox.add(balanceField);
	}

	protected void addButtons(JPanel buttonPanel) {
		buttonPanel.add(new JButton(new DepositAction()));
		buttonPanel.add(new JButton(new WithdrawAction()));
		buttonPanel.add(new JButton(new TransferAction()));
	}
	
	private class DepositAction extends RobustAction {
		
		DepositAction() {
			super("Deposit...");
		}

		public void action() throws Exception {
            Number amount = enterAmount("Deposit");
            if (amount == null) return;
			_prevayler.execute(new Deposit(account, amount.longValue()));
		}
	}

	private class WithdrawAction extends RobustAction {
		
		WithdrawAction() {
			super("Withdraw...");
		}

        public void action() throws Exception {
            Number amount = enterAmount("Withdrawal");
            if (amount == null) return;
			_prevayler.execute(new Withdrawal(account, amount.longValue()));
		}
	}

    private Number enterAmount(String operation) throws Exception {
        String amountText = JOptionPane.showInputDialog(null,"Enter amount",operation,JOptionPane.PLAIN_MESSAGE);
        if (amountText == null) return null;
        return new Long(parse(amountText));
    }

	private class TransferAction extends RobustAction {
		
		TransferAction() {
			super("Transfer...");
		}

		public void action() {
            new TransferFrame(account, _prevayler, getDesktopPane());
		}
	}

	public void accountChanged() {  //Implements AccountListener.
		holderField.setText(account.holder());
		historyList.setListData(account.transactionHistory().toArray());
		balanceField.setText(String.valueOf(account.balance()));
	}
	
	private class HolderListener extends FocusAdapter {
		public void focusLost(FocusEvent e) {
			if (holderText().equals(account.holder())) return;
			try {
				_prevayler.execute(new HolderChange(account, holderText()));
			} catch (Exception exception) {
				RobustAction.display(exception);
			}
		}
	}
}
