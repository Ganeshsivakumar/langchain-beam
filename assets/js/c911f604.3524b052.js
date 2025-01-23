"use strict";(self.webpackChunkdocs_site=self.webpackChunkdocs_site||[]).push([[205],{4500:(e,n,t)=>{t.r(n),t.d(n,{assets:()=>d,contentTitle:()=>r,default:()=>p,frontMatter:()=>s,metadata:()=>o,toc:()=>l});const o=JSON.parse('{"id":"integrations/models","title":"Models","description":"The integration of Large Language Models is managed by Langchain-Beam library using langchain. Pipeline authors just need to specify the provider and the model that they want to use with the","source":"@site/docs/integrations/models.md","sourceDirName":"integrations","slug":"/integrations/models","permalink":"/langchain-beam/docs/integrations/models","draft":false,"unlisted":false,"editUrl":"https://github.com/Ganeshsivakumar/langchain-beam/tree/main/docs/docs-site/docs/docs/integrations/models.md","tags":[],"version":"current","frontMatter":{},"sidebar":"tutorialSidebar","previous":{"title":"Integrations","permalink":"/langchain-beam/docs/category/integrations"}}');var i=t(4848),a=t(8453);const s={},r="Models",d={},l=[];function c(e){const n={code:"code",h1:"h1",header:"header",p:"p",pre:"pre",...(0,a.R)(),...e.components};return(0,i.jsxs)(i.Fragment,{children:[(0,i.jsx)(n.header,{children:(0,i.jsx)(n.h1,{id:"models",children:"Models"})}),"\n",(0,i.jsx)(n.p,{children:"The integration of Large Language Models is managed by Langchain-Beam library using langchain. Pipeline authors just need to specify the provider and the model that they want to use with the\ntransform in beam pipeline."}),"\n",(0,i.jsx)(n.p,{children:"Each model provider comes with its own dedicated options class within the library. For example, if you wish to use OpenAI's models, you can configure them using the corresponding options class."}),"\n",(0,i.jsx)(n.pre,{children:(0,i.jsx)(n.code,{className:"language-java",children:'//import the OpenAi options class\nimport com.langchainbeam.model.openai.OpenAiModelOptions;\nimport com.langchainbeam.LangchainModelHandler;\n\n// create model options\nOpenAiModelOptions modelOptions = OpenAiModelOptions.builder()\n                .modelName("gpt-4o-mini")\n                .apiKey(apiKey)\n                .temperature(0.7)\n                .build();\n\n// pass the options to handler\nLangchainModelHandler handler = new LangchainModelHandler(instructionPrompt, modelOptions);\n\n//create transform\nLangchainBeam.run(handler);\n'})}),"\n",(0,i.jsx)(n.p,{children:"Example for Anthropic model :"}),"\n",(0,i.jsx)(n.pre,{children:(0,i.jsx)(n.code,{className:"language-java",children:'import com.langchainbeam.model.anthropic.AnthropicModelOptions;\n\nAnthropicModelOptions modelOptions1 = AnthropicModelOptions.builder()\n                .modelName("claude-3-5-sonnet-latest")\n                .apiKey(ANTHROPIC_API_KEY)\n                .build();\n'})}),"\n",(0,i.jsx)(n.p,{children:"Additional providers will be integrated in future releases. If you need support for a specific provider or are interested in contributing to the integration of new model providers, please feel free to create a GitHub issue."})]})}function p(e={}){const{wrapper:n}={...(0,a.R)(),...e.components};return n?(0,i.jsx)(n,{...e,children:(0,i.jsx)(c,{...e})}):c(e)}},8453:(e,n,t)=>{t.d(n,{R:()=>s,x:()=>r});var o=t(6540);const i={},a=o.createContext(i);function s(e){const n=o.useContext(a);return o.useMemo((function(){return"function"==typeof e?e(n):{...n,...e}}),[n,e])}function r(e){let n;return n=e.disableParentContext?"function"==typeof e.components?e.components(i):e.components||i:s(e.components),o.createElement(a.Provider,{value:n},e.children)}}}]);