/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.*;

public class SingleTypeReference extends TypeReference {

	public char[] token;

	public SingleTypeReference(char[] source, long pos) {

			token = source;
			sourceStart = (int) (pos>>>32)  ;
			sourceEnd = (int) (pos & 0x00000000FFFFFFFFL) ;
		
	}

	public TypeReference copyDims(int dim){
		//return a type reference copy of me with some dimensions
		//warning : the new type ref has a null binding
		
		return new ArrayTypeReference(token, dim,(((long)sourceStart)<<32)+sourceEnd);
	}

	public TypeBinding getTypeBinding(Scope scope) {
		if (this.resolvedType != null)
			return this.resolvedType;
		return scope.getType(token);
	}

	public char [][] getTypeName() {
		return new char[][] { token };
	}

	public StringBuffer printExpression(int indent, StringBuffer output){
		
		return output.append(token);
	}

	public TypeBinding resolveTypeEnclosing(BlockScope scope, ReferenceBinding enclosingType) {

		ReferenceBinding memberTb = scope.getMemberType(token, enclosingType);
		if (!memberTb.isValidBinding()) {
			scope.problemReporter().invalidEnclosingType(this, memberTb, enclosingType);
			return null;
		}
		if (isTypeUseDeprecated(memberTb, scope))
			scope.problemReporter().deprecatedType(memberTb, this);
		return this.resolvedType = memberTb;
	}

	public void traverse(ASTVisitor visitor, BlockScope scope) {
		visitor.visit(this, scope);
		visitor.endVisit(this, scope);
	}

	public void traverse(ASTVisitor visitor, ClassScope scope) {
		visitor.visit(this, scope);
		visitor.endVisit(this, scope);
	}
}
