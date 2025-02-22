import { themes as prismThemes } from "prism-react-renderer";
import type { Config } from "@docusaurus/types";
import type * as Preset from "@docusaurus/preset-classic";

// This runs in Node.js - Don't use client-side code here (browser APIs, JSX...)

const config: Config = {
  title: "langchain-beam",
  tagline: "Langchain-beam docs",
  favicon: "img/favicon.ico",

  // Set the production url of your site here
  url: "https://github.com",
  // Set the /<baseUrl>/ pathname under which your site is served
  // For GitHub pages deployment, it is often '/<projectName>/'
  baseUrl: "/langchain-beam/",
  trailingSlash: true,

  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  organizationName: "Ganeshsivakumar", // Usually your GitHub org/user name.
  projectName: "langchain-beam", // Usually your repo name.

  onBrokenLinks: "throw",
  onBrokenMarkdownLinks: "warn",

  // Even if you don't use internationalization, you can use this field to set
  // useful metadata like html lang. For example, if your site is Chinese, you
  // may want to replace "en" with "zh-Hans".
  i18n: {
    defaultLocale: "en",
    locales: ["en"],
  },

  presets: [
    [
      "classic",
      {
        docs: {
          sidebarPath: "./sidebars.ts",
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
            "https://github.com/Ganeshsivakumar/langchain-beam/tree/main/docs/docs-site/docs",
        },
        /*blog: {
          showReadingTime: true,
          feedOptions: {
            type: ["rss", "atom"],
            xslt: true,
          },
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
            "https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/",
          // Useful options to enforce blogging best practices
          onInlineTags: "warn",
          onInlineAuthors: "warn",
          onUntruncatedBlogPosts: "warn",
        },*/
        theme: {
          customCss: "./src/css/custom.css",
        },
        gtag: {
          trackingID: "G-LJN8GV9JEH",
          anonymizeIP: true,
        },
      } satisfies Preset.Options,
    ],
  ],

  themeConfig: {
    // Replace with your project's social card
    image: "img/logo.png",
    metadata: [
      {
        name: "keywords",
        content: "apache beam, langchain, langchain-beam, PTransform",
      },
    ],
    navbar: {
      title: "Langchain-Beam",
      logo: {
        alt: "langchain-beam logo",
        src: "img/logo.png",
      },

      items: [
        {
          type: "docSidebar",
          sidebarId: "tutorialSidebar",
          position: "left",
          label: "Docs",
        },
        //{ to: "/blog", label: "Blog", position: "left" },
        {
          href: "https://javadoc.io/doc/io.github.ganeshsivakumar/langchain-beam/latest/index.html",
          label: "Javadoc",
          position: "right",
        },
        {
          href: "https://github.com/Ganeshsivakumar/langchain-beam/tree/main/example/langchain-beam-example/src/main/java/com/langchainbeam/example",
          label: "Examples",
          position: "right",
        },
        {
          href: "https://github.com/Ganeshsivakumar/langchain-beam",
          label: "GitHub",
          position: "right",
        },
      ],
    },
    footer: {
      style: "dark",
      links: [
        {
          title: "Docs",
          items: [
            {
              label: "Tutorial",
              to: "/docs/intro",
            },
          ],
        },
        /*{
          title: "Community",
          items: [
            {
              label: "Stack Overflow",
              href: "https://stackoverflow.com/questions/tagged/docusaurus",
            },
            {
              label: "Discord",
              href: "https://discordapp.com/invite/docusaurus",
            },
            {
              label: "X",
              href: "https://x.com/docusaurus",
            },
          ],
        },*/
        {
          title: "More",
          items: [
            /*{
              label: "Blog",
              to: "/blog",
            },*/
            {
              label: "GitHub",
              href: "https://github.com/Ganeshsivakumar/langchain-beam",
            },
          ],
        },
      ],
    },
    prism: {
      theme: prismThemes.github,
      darkTheme: prismThemes.dracula,
      additionalLanguages: ["java"],
    },
  } satisfies Preset.ThemeConfig,
};

export default config;
