package com.df4j.v2.boot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;
import java.util.Map;

/**
 * SpringBoot datasource配置
 */
@ConfigurationProperties(prefix = "df.boot.datasource")
public class DfBootDatasourceProperties {
    // 是否启用自定义数据源配置
    private boolean enabled;
    // 默认数据源
    private String defaultKey;
    // 数据源配置
    private Map<String, DatasourceNodeProperties> datasources;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDefaultKey() {
        return defaultKey;
    }

    public void setDefaultKey(String defaultKey) {
        this.defaultKey = defaultKey;
    }

    public Map<String, DatasourceNodeProperties> getDatasources() {
        return datasources;
    }

    public void setDatasources(Map<String, DatasourceNodeProperties> datasources) {
        this.datasources = datasources;
    }

    /**
     * 数据源节点配置
     */
    public static class DatasourceNodeProperties{
        // 数据源类型
        private String type;

        // 是否启用
        private boolean enabled = true;

        // 适用的module名称
        private List<String> modules;

        // 主节点key
        private String master;

        // 开启读写分离，支持读库
        private boolean enableReadNodes = false;

        // 详细节点配置
        private Map<String, Map<String, Object>> nodes;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public List<String> getModules() {
            return modules;
        }

        public void setModules(List<String> modules) {
            this.modules = modules;
        }

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public boolean isEnableReadNodes() {
            return enableReadNodes;
        }

        public void setEnableReadNodes(boolean enableReadNodes) {
            this.enableReadNodes = enableReadNodes;
        }

        public Map<String, Map<String, Object>> getNodes() {
            return nodes;
        }

        public void setNodes(Map<String, Map<String, Object>> nodes) {
            this.nodes = nodes;
        }
    }
}
