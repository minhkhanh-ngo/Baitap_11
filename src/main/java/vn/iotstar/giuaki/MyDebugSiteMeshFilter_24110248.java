package vn.iotstar.giuaki;

import org.sitemesh.DecoratorSelector;
import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.Content;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.webapp.WebAppContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyDebugSiteMeshFilter_24110248 extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        super.applyCustomConfiguration(builder);

        // 1. Các trang xác thực (login, register, verify) chỉ dùng layout có footer, bỏ header
        builder.addDecoratorPath("/login*", "/decorators/auth.jsp");
        builder.addDecoratorPath("/register*", "/decorators/auth.jsp");
        builder.addDecoratorPath("/verify*", "/decorators/auth.jsp");

        builder.addDecoratorPath("/admin/*", "/decorators/admin.jsp");

        builder.addDecoratorPath("/*", "/decorators/user.jsp");

        DecoratorSelector defaultSelector = builder.getDecoratorSelector();
        builder.setCustomDecoratorSelector(new DecoratorSelector() {
            @Override
            public String[] selectDecoratorPaths(Content content, SiteMeshContext context) throws IOException {
                String[] paths = defaultSelector.selectDecoratorPaths(content, context);
                if (context instanceof WebAppContext) {
                    HttpServletRequest req = ((WebAppContext) context).getRequest();
                    if (req != null) {
                        System.out.println(">>> URL: " + req.getRequestURI() + " --> Decorator: " + paths[0]);
                    }
                }
                return paths;
            }
        });
    }
}