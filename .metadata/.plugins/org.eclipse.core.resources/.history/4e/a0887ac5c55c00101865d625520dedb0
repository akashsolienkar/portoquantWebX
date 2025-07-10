package com.quant.portoquant.infrastructure.reports.renderer;



import com.quant.portoquant.infrastructure.reports.models.PortfolioReportData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Renders Thymeleaf template with report data into HTML.
 * Used before converting HTML to PDF.
 *
 * @author akash
 */
@Component
public class ThymeleafReportRenderer {

    private final TemplateEngine templateEngine;

    @Autowired
    public ThymeleafReportRenderer(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Renders the portfolio report template as HTML.
     *
     * @param reportData the data for the report
     * @return rendered HTML as string
     */
    public String renderPortfolioReport(PortfolioReportData reportData) {
        Context context = new Context();
        context.setVariable("report", reportData);
        return templateEngine.process("portfolio-report", context); // looks in src/main/resources/templates/
    }
}
