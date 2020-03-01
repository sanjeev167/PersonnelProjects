/**
 * 
 */
package com.config.filters;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

/**
 * @author Sanjeev
 * 
 *         This is a filter where different paths are configured with a specific
 *         decorators and some urls are also excluded with the decorators.
 *
 */
public class CustomSiteMeshFilter extends ConfigurableSiteMeshFilter {
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder
		        // Exclude path from decoration.
		        .addExcludedPath("/resources/*")
				.addExcludedPath("/hm/*")		
				.addExcludedPath("/user/pub/*")
				.addExcludedPath("/admin/pub/*")
				.setIncludeErrorPages(true)	
				// Map default decorator. This shall be applied to all paths if no other paths match.
			  //  .addDecoratorPath("/*", "/WEB-INF/sitemesh/adminDctr.jsp") 
				
				
				// Map admin decorators to specific path patterns.				
				.addDecoratorPath("/admin/pvt/*", "/WEB-INF/sitemesh/adminDctr.jsp")
				
				.addDecoratorPath("/ce/*", "/WEB-INF/sitemesh/adminDctr.jsp")
				

				// Map user decorators to specific path patterns.
				.addDecoratorPath("/user/pvt/*", "/WEB-INF/sitemesh/userDctr.jsp")
				.create();
		       // Assigning default decorator if no path specific decorator found
  
	}// End of applyCustomConfiguration

}// End of CustomSiteMeshFilter