import defaultSettings from '@/settings'

const title = defaultSettings.title || 'Express Delivery'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
