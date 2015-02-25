package especies

import grails.transaction.Transactional

@Transactional
class TaxonService {

    List list(params) {
		params = params + [max: 50]
		params = params + [sort: "scientificName", order: "asc"]
		def query
		if(params.get('query') != null) {
			query = Taxon.where {
				scientificName =~ params.get('query')+'%'
			}
			return query.list(params)
			
		}
		return Taxon.list(params)
    }
	
	Taxon save(taxon) {
		taxon.save()
	}
	
	Taxon addGbifDetails(scientificName, sourceId, gbifId, gbifName) {
		Taxon taxon
		if(sourceId == "#N/A") {
			taxon = Taxon.findByScientificNameLike(scientificName)
		} else {
			taxon = Taxon.findBySourceId(sourceId)
		}
		if(taxon != null && gbifId != "NULL") {
			taxon.gbifId = Integer.parseInt(gbifId)
			taxon.gbifName = gbifName
			taxon.save()
		}
		taxon
	}
	
	Taxon addSpeciesPlusId(gbifId, speciesPlusId) {
		Taxon taxon = Taxon.findByGbifId(gbifId)
		if(taxon != null) {
			taxon.speciesPlusId = speciesPlusId
			taxon.save()
		}
		taxon
	}
}
