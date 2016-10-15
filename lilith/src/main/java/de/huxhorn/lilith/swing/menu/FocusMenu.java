/*
 * Lilith - a log event viewer.
 * Copyright (C) 2007-2016 Joern Huxhorn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.huxhorn.lilith.swing.menu;

import de.huxhorn.lilith.data.access.AccessEvent;
import de.huxhorn.lilith.data.eventsource.EventWrapper;
import de.huxhorn.lilith.data.logging.LoggingEvent;
import de.huxhorn.lilith.swing.ApplicationPreferences;
import de.huxhorn.lilith.swing.actions.AbstractAccessFilterAction;
import de.huxhorn.lilith.swing.actions.AbstractLoggingFilterAction;
import de.huxhorn.lilith.swing.actions.FilterAction;
import de.huxhorn.lilith.swing.actions.FocusCallLocationAction;
import de.huxhorn.lilith.swing.actions.FocusFormattedMessageAction;
import de.huxhorn.lilith.swing.actions.FocusHttpMethodAction;
import de.huxhorn.lilith.swing.actions.FocusHttpRemoteUserAction;
import de.huxhorn.lilith.swing.actions.FocusHttpRequestUrlAction;
import de.huxhorn.lilith.swing.actions.FocusHttpStatusCodeAction;
import de.huxhorn.lilith.swing.actions.FocusMessagePatternAction;
import de.huxhorn.lilith.swing.actions.FocusThreadGroupNameAction;
import de.huxhorn.lilith.swing.actions.FocusThreadNameAction;
import de.huxhorn.lilith.swing.actions.FocusThrowableAction;
import de.huxhorn.lilith.swing.actions.FocusThrowablesAction;
import javax.swing.JMenuItem;
import java.util.List;

public class FocusMenu
	extends AbstractFilterMenu
{
	private static final long serialVersionUID = 2301518754828320721L;

	private final ApplicationPreferences applicationPreferences;
	private final boolean htmlTooltip;

	private EventWrapper eventWrapper;

	private FocusSavedConditionsMenu savedMenu;

	private FocusLoggerMenu loggerMenu;

	private FilterAction messagePatternAction;
	private JMenuItem messagePatternItem;
	private FilterAction formattedMessageAction;
	private JMenuItem formattedMessageItem;

	private FocusLevelMenu levelMenu;

	private FilterAction callLocationAction;
	private JMenuItem callLocationItem;

	private FilterAction throwablesAction;
	private JMenuItem throwablesItem;
	private FilterAction throwableAction;
	private JMenuItem throwableItem;

	private FilterAction threadNameAction;
	private JMenuItem threadNameItem;
	private FilterAction threadGroupNameAction;
	private JMenuItem threadGroupNameItem;

	private FocusMDCMenu mdcMenu;
	private FocusMarkerMenu markerMenu;
	private FocusNDCMenu ndcMenu;

	private FilterAction statusCodeAction;
	private JMenuItem statusCodeItem;
	private FocusHttpStatusTypeMenu statusTypeMenu;

	private FilterAction methodAction;
	private JMenuItem methodItem;

	private FocusHttpRequestUriMenu requestUriMenu;
	private FilterAction requestUrlAction;
	private JMenuItem requestUrlItem;

	private FilterAction remoteUserAction;
	private JMenuItem remoteUserItem;

	public FocusMenu(ApplicationPreferences applicationPreferences, boolean htmlTooltip)
	{
		super("Focus");
		this.applicationPreferences = applicationPreferences;
		this.htmlTooltip = htmlTooltip;
		createUI();
		setViewContainer(null);
		setEventWrapper(null);
	}

	private void createUI()
	{
		savedMenu = new FocusSavedConditionsMenu(applicationPreferences, htmlTooltip);

		messagePatternAction = new FocusMessagePatternAction(htmlTooltip);
		messagePatternItem = new JMenuItem(messagePatternAction);

		formattedMessageAction = new FocusFormattedMessageAction(htmlTooltip);
		formattedMessageItem = new JMenuItem(formattedMessageAction);

		callLocationAction = new FocusCallLocationAction();
		callLocationItem = new JMenuItem(callLocationAction);

		throwablesAction = new FocusThrowablesAction();
		throwablesItem = new JMenuItem(throwablesAction);

		throwableAction = new FocusThrowableAction();
		throwableItem = new JMenuItem(throwableAction);

		threadNameAction = new FocusThreadNameAction();
		threadNameItem = new JMenuItem(threadNameAction);

		threadGroupNameAction = new FocusThreadGroupNameAction();
		threadGroupNameItem = new JMenuItem(threadGroupNameAction);

		mdcMenu = new FocusMDCMenu();
		markerMenu = new FocusMarkerMenu();
		ndcMenu = new FocusNDCMenu(htmlTooltip);
		loggerMenu = new FocusLoggerMenu();
		levelMenu = new FocusLevelMenu();

		statusCodeAction = new FocusHttpStatusCodeAction();
		statusCodeItem = new JMenuItem(statusCodeAction);

		statusTypeMenu = new FocusHttpStatusTypeMenu();

		methodAction = new FocusHttpMethodAction();
		methodItem = new JMenuItem(methodAction);

		requestUriMenu = new FocusHttpRequestUriMenu();

		requestUrlAction = new FocusHttpRequestUrlAction();
		requestUrlItem = new JMenuItem(requestUrlAction);

		remoteUserAction = new FocusHttpRemoteUserAction();
		remoteUserItem = new JMenuItem(remoteUserAction);
	}

	public void setEventWrapper(EventWrapper eventWrapper)
	{
		this.eventWrapper = eventWrapper;
		updateState();
	}

	@Override
	protected void viewContainerUpdated()
	{
		savedMenu.setViewContainer(viewContainer);

		loggerMenu.setViewContainer(viewContainer);

		messagePatternAction.setViewContainer(viewContainer);
		formattedMessageAction.setViewContainer(viewContainer);

		levelMenu.setViewContainer(viewContainer);

		callLocationAction.setViewContainer(viewContainer);

		throwablesAction.setViewContainer(viewContainer);
		throwableAction.setViewContainer(viewContainer);

		threadNameAction.setViewContainer(viewContainer);
		threadGroupNameAction.setViewContainer(viewContainer);

		mdcMenu.setViewContainer(viewContainer);
		markerMenu.setViewContainer(viewContainer);
		ndcMenu.setViewContainer(viewContainer);

		statusCodeAction.setViewContainer(viewContainer);
		statusTypeMenu.setViewContainer(viewContainer);

		methodAction.setViewContainer(viewContainer);

		requestUriMenu.setViewContainer(viewContainer);
		requestUrlAction.setViewContainer(viewContainer);

		remoteUserAction.setViewContainer(viewContainer);

		super.viewContainerUpdated();
	}

	private void updateState()
	{
		removeAll();

		LoggingEvent loggingEvent = AbstractLoggingFilterAction.resolveLoggingEvent(eventWrapper);
		if(loggingEvent != null)
		{
			savedMenu.setEventWrapper(eventWrapper);

			loggerMenu.setEventWrapper(eventWrapper);

			messagePatternAction.setEventWrapper(eventWrapper);
			formattedMessageAction.setEventWrapper(eventWrapper);

			levelMenu.setEventWrapper(eventWrapper);

			callLocationAction.setEventWrapper(eventWrapper);

			throwablesAction.setEventWrapper(eventWrapper);
			throwableAction.setEventWrapper(eventWrapper);

			threadNameAction.setEventWrapper(eventWrapper);
			threadGroupNameAction.setEventWrapper(eventWrapper);

			mdcMenu.setEventWrapper(eventWrapper);
			markerMenu.setEventWrapper(eventWrapper);
			ndcMenu.setEventWrapper(eventWrapper);

			add(savedMenu);
			addSeparator();
			add(loggerMenu);
			addSeparator();
			add(messagePatternItem);
			add(formattedMessageItem);
			addSeparator();
			add(levelMenu);
			addSeparator();
			add(callLocationItem);
			addSeparator();
			add(throwablesItem);
			add(throwableItem);
			addSeparator();
			add(threadNameItem);
			add(threadGroupNameItem);
			addSeparator();
			add(mdcMenu);
			add(markerMenu);
			add(ndcMenu);

			// levelMenu and throwablesAction will always be enabled if an event exists at all
			setEnabled(true);
			return;
		}

		AccessEvent accessEvent = AbstractAccessFilterAction.resolveAccessEvent(eventWrapper);
		if(accessEvent != null)
		{
			savedMenu.setEventWrapper(eventWrapper);

			statusCodeAction.setEventWrapper(eventWrapper);
			statusTypeMenu.setEventWrapper(eventWrapper);

			methodAction.setEventWrapper(eventWrapper);

			requestUriMenu.setEventWrapper(eventWrapper);
			requestUrlAction.setEventWrapper(eventWrapper);

			remoteUserAction.setEventWrapper(eventWrapper);

			add(savedMenu);
			addSeparator();
			add(statusCodeItem);
			add(statusTypeMenu);
			addSeparator();
			add(methodItem);
			addSeparator();
			add(requestUriMenu);
			add(requestUrlItem);
			addSeparator();
			add(remoteUserItem);

			// statusTypeMenu will always be enabled if an event exists at all
			setEnabled(true);
			return;
		}

		setEnabled(false);
	}

	public void setConditionNames(List<String> conditionNames)
	{
		savedMenu.setConditionNames(conditionNames);
	}
}
