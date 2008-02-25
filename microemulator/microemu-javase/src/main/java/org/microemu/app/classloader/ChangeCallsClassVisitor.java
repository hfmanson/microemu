/**
 *  MicroEmulator
 *  Copyright (C) 2006-2007 Bartek Teodorczyk <barteo@barteo.net>
 *  Copyright (C) 2006-2007 Vlad Skarzhevskyy
 *
 *  It is licensed under the following two licenses as alternatives:
 *    1. GNU Lesser General Public License (the "LGPL") version 2.1 or any newer version
 *    2. Apache License (the "AL") Version 2.0
 *
 *  You may not use this file except in compliance with at least one of
 *  the above two licenses.
 *
 *  You may obtain a copy of the LGPL at
 *      http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt
 *
 *  You may obtain a copy of the AL at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the LGPL or the AL for the specific language governing permissions and
 *  limitations.
 *
 *  @version $Id$
 */
package org.microemu.app.classloader;

import org.microemu.app.util.MIDletThread;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * @author vlads
 *
 */
public class ChangeCallsClassVisitor extends ClassAdapter {

	InstrumentationConfig config;
	
	public ChangeCallsClassVisitor(ClassVisitor cv, InstrumentationConfig config) {
		super(cv);
		this.config = config;
	}

    public void visit(final int version, final int access, final String name, final String signature, String superName, final String[] interfaces) {
    	if  ((config.isEnhanceThreadCreation()) && (superName.equals("java/lang/Thread"))) {
    		superName = ChangeCallsMethodVisitor.codeName(MIDletThread.class);
    	}
    	super.visit(version, access, name, signature, superName, interfaces);
	}
    
	public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
		return  new ChangeCallsMethodVisitor(super.visitMethod(access, name, desc, signature, exceptions), config);
	}

}
