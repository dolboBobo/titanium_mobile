/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2016 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.modules.titanium.ui;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;

import ti.modules.titanium.ui.widget.TiUILabel;
// clang-format off
@Kroll.proxy(creatableInModule = UIModule.class,
	propertyAccessors = {
		TiC.PROPERTY_AUTO_LINK,
		TiC.PROPERTY_ATTRIBUTED_STRING,
		TiC.PROPERTY_COLOR,
		TiC.PROPERTY_ELLIPSIZE,
		TiC.PROPERTY_FONT,
		TiC.PROPERTY_HIGHLIGHTED_COLOR,
		TiC.PROPERTY_HTML,
		TiC.PROPERTY_TEXT,
		TiC.PROPERTY_TEXT_ALIGN,
		TiC.PROPERTY_TEXTID,
		TiC.PROPERTY_WORD_WRAP,
		TiC.PROPERTY_VERTICAL_ALIGN,
		TiC.PROPERTY_SHADOW_OFFSET,
		TiC.PROPERTY_SHADOW_COLOR,
		TiC.PROPERTY_SHADOW_RADIUS,
		TiC.PROPERTY_LINES,
		TiC.PROPERTY_MAX_LINES,
		TiC.PROPERTY_LINE_SPACING,
		TiC.PROPERTY_INCLUDE_FONT_PADDING,
		TiC.PROPERTY_MINIMUM_FONT_SIZE
})
// clang-format on
public class LabelProxy extends TiViewProxy
{
	private static final String TAG = "LabelProxy";
	private static final int MSG_FIRST_ID = TiViewProxy.MSG_LAST_ID + 1;
	protected static final int MSG_LAST_ID = MSG_FIRST_ID + 999;

	public LabelProxy()
	{
		defaultValues.put(TiC.PROPERTY_TEXT, "");
		defaultValues.put(TiC.PROPERTY_ELLIPSIZE, UIModule.TEXT_ELLIPSIZE_TRUNCATE_END);
		defaultValues.put(TiC.PROPERTY_SHADOW_RADIUS, 1f);
	}

	@Override
	protected KrollDict getLangConversionTable()
	{
		KrollDict table = new KrollDict();
		table.put(TiC.PROPERTY_TEXT, TiC.PROPERTY_TEXTID);
		return table;
	}

	@Override
	public TiUIView createView(Activity activity)
	{
		return new TiUILabel(this);
	}

	@Override
	public void handleCreationDict(KrollDict properties)
	{
		// Validate argument.
		if (properties == null) {
			return;
		}

		// Let the base class handle it first. Will localize given properties if needed.
		super.handleCreationDict(properties);

		// Handle deprecated "wordWrap" property.
		// Note: Not suported on iOS either. Is supported on Windows.
		if (properties.containsKey(TiC.PROPERTY_WORD_WRAP)) {
			logWordWrapWarning();
		} else {
			setProperty(TiC.PROPERTY_WORD_WRAP, true);
		}
	}

	@Override
	public void onPropertyChanged(String name, Object value)
	{
		// Let the base class handle it first. Will localize property value if needed.
		super.onPropertyChanged(name, value);

		// Handle property change.
		if (TiC.PROPERTY_WORD_WRAP.equals(name)) {
			logWordWrapWarning();
		}
	}

	private void logWordWrapWarning()
	{
		String message = "Ti.UI.Label property '" + TiC.PROPERTY_WORD_WRAP + "' is deprecated. "
						 + "If setting false, then set '" + TiC.PROPERTY_MAX_LINES + "' to 1 instead.";
		Log.w(TAG, message);
	}

	@Override
	public String getApiName()
	{
		return "Ti.UI.Label";
	}
}
