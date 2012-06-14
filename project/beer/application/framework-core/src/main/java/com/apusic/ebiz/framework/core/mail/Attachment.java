package com.apusic.ebiz.framework.core.mail;

import java.io.File;

public class Attachment {
	private String attachmentFilename;
	private File attachementFile;
	private boolean isInline;

	public String getAttachmentFilename() {
		return attachmentFilename;
	}

	public void setAttachmentFilename(String attachmentFilename) {
		this.attachmentFilename = attachmentFilename;
	}

	public File getAttachementFile() {
		return attachementFile;
	}

	public void setAttachementFile(File attachementFile) {
		this.attachementFile = attachementFile;
	}

	public boolean isInline() {
		return isInline;
	}

	public void setInline(boolean isInline) {
		this.isInline = isInline;
	}
}
