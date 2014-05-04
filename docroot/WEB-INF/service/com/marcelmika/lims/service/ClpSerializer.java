/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.marcelmika.lims.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassLoaderObjectInputStream;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;

import com.marcelmika.lims.model.BuddyClp;
import com.marcelmika.lims.model.ConversationClp;
import com.marcelmika.lims.model.OpenedConversationClp;
import com.marcelmika.lims.model.PanelClp;
import com.marcelmika.lims.model.SettingsClp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ClpSerializer {
	public static String getServletContextName() {
		if (Validator.isNotNull(_servletContextName)) {
			return _servletContextName;
		}

		synchronized (ClpSerializer.class) {
			if (Validator.isNotNull(_servletContextName)) {
				return _servletContextName;
			}

			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Class<?> portletPropsClass = classLoader.loadClass(
						"com.liferay.util.portlet.PortletProps");

				Method getMethod = portletPropsClass.getMethod("get",
						new Class<?>[] { String.class });

				String portletPropsServletContextName = (String)getMethod.invoke(null,
						"liferay-lims-portlet-deployment-context");

				if (Validator.isNotNull(portletPropsServletContextName)) {
					_servletContextName = portletPropsServletContextName;
				}
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Unable to locate deployment context from portlet properties");
				}
			}

			if (Validator.isNull(_servletContextName)) {
				try {
					String propsUtilServletContextName = PropsUtil.get(
							"liferay-lims-portlet-deployment-context");

					if (Validator.isNotNull(propsUtilServletContextName)) {
						_servletContextName = propsUtilServletContextName;
					}
				}
				catch (Throwable t) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Unable to locate deployment context from portal properties");
					}
				}
			}

			if (Validator.isNull(_servletContextName)) {
				_servletContextName = "liferay-lims-portlet";
			}

			return _servletContextName;
		}
	}

	public static Object translateInput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		if (oldModelClassName.equals(BuddyClp.class.getName())) {
			return translateInputBuddy(oldModel);
		}

		if (oldModelClassName.equals(ConversationClp.class.getName())) {
			return translateInputConversation(oldModel);
		}

		if (oldModelClassName.equals(OpenedConversationClp.class.getName())) {
			return translateInputOpenedConversation(oldModel);
		}

		if (oldModelClassName.equals(PanelClp.class.getName())) {
			return translateInputPanel(oldModel);
		}

		if (oldModelClassName.equals(SettingsClp.class.getName())) {
			return translateInputSettings(oldModel);
		}

		return oldModel;
	}

	public static Object translateInput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateInput(curObj));
		}

		return newList;
	}

	public static Object translateInputBuddy(BaseModel<?> oldModel) {
		BuddyClp oldClpModel = (BuddyClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getBuddyRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputConversation(BaseModel<?> oldModel) {
		ConversationClp oldClpModel = (ConversationClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getConversationRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputOpenedConversation(BaseModel<?> oldModel) {
		OpenedConversationClp oldClpModel = (OpenedConversationClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getOpenedConversationRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputPanel(BaseModel<?> oldModel) {
		PanelClp oldClpModel = (PanelClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getPanelRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputSettings(BaseModel<?> oldModel) {
		SettingsClp oldClpModel = (SettingsClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getSettingsRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateInput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateInput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	public static Object translateOutput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		if (oldModelClassName.equals("com.marcelmika.lims.model.impl.BuddyImpl")) {
			return translateOutputBuddy(oldModel);
		}

		if (oldModelClassName.equals(
					"com.marcelmika.lims.model.impl.ConversationImpl")) {
			return translateOutputConversation(oldModel);
		}

		if (oldModelClassName.equals(
					"com.marcelmika.lims.model.impl.OpenedConversationImpl")) {
			return translateOutputOpenedConversation(oldModel);
		}

		if (oldModelClassName.equals("com.marcelmika.lims.model.impl.PanelImpl")) {
			return translateOutputPanel(oldModel);
		}

		if (oldModelClassName.equals(
					"com.marcelmika.lims.model.impl.SettingsImpl")) {
			return translateOutputSettings(oldModel);
		}

		return oldModel;
	}

	public static Object translateOutput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateOutput(curObj));
		}

		return newList;
	}

	public static Object translateOutput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateOutput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateOutput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	public static Throwable translateThrowable(Throwable throwable) {
		if (_useReflectionToTranslateThrowable) {
			try {
				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(unsyncByteArrayOutputStream);

				objectOutputStream.writeObject(throwable);

				objectOutputStream.flush();
				objectOutputStream.close();

				UnsyncByteArrayInputStream unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(unsyncByteArrayOutputStream.unsafeGetByteArray(),
						0, unsyncByteArrayOutputStream.size());

				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader = currentThread.getContextClassLoader();

				ObjectInputStream objectInputStream = new ClassLoaderObjectInputStream(unsyncByteArrayInputStream,
						contextClassLoader);

				throwable = (Throwable)objectInputStream.readObject();

				objectInputStream.close();

				return throwable;
			}
			catch (SecurityException se) {
				if (_log.isInfoEnabled()) {
					_log.info("Do not use reflection to translate throwable");
				}

				_useReflectionToTranslateThrowable = false;
			}
			catch (Throwable throwable2) {
				_log.error(throwable2, throwable2);

				return throwable2;
			}
		}

		Class<?> clazz = throwable.getClass();

		String className = clazz.getName();

		if (className.equals(PortalException.class.getName())) {
			return new PortalException();
		}

		if (className.equals(SystemException.class.getName())) {
			return new SystemException();
		}

		if (className.equals("com.marcelmika.lims.NoSuchBuddyException")) {
			return new com.marcelmika.lims.NoSuchBuddyException();
		}

		if (className.equals("com.marcelmika.lims.NoSuchConversationException")) {
			return new com.marcelmika.lims.NoSuchConversationException();
		}

		if (className.equals(
					"com.marcelmika.lims.NoSuchOpenedConversationException")) {
			return new com.marcelmika.lims.NoSuchOpenedConversationException();
		}

		if (className.equals("com.marcelmika.lims.NoSuchPanelException")) {
			return new com.marcelmika.lims.NoSuchPanelException();
		}

		if (className.equals("com.marcelmika.lims.NoSuchSettingsException")) {
			return new com.marcelmika.lims.NoSuchSettingsException();
		}

		return throwable;
	}

	public static Object translateOutputBuddy(BaseModel<?> oldModel) {
		BuddyClp newModel = new BuddyClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setBuddyRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputConversation(BaseModel<?> oldModel) {
		ConversationClp newModel = new ConversationClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setConversationRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputOpenedConversation(
		BaseModel<?> oldModel) {
		OpenedConversationClp newModel = new OpenedConversationClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setOpenedConversationRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputPanel(BaseModel<?> oldModel) {
		PanelClp newModel = new PanelClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setPanelRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputSettings(BaseModel<?> oldModel) {
		SettingsClp newModel = new SettingsClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setSettingsRemoteModel(oldModel);

		return newModel;
	}

	private static Log _log = LogFactoryUtil.getLog(ClpSerializer.class);
	private static String _servletContextName;
	private static boolean _useReflectionToTranslateThrowable = true;
}