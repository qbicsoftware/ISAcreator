/**
 ISAcreator is a component of the ISA software suite (http://www.isa-tools.org)

 License:
 ISAcreator is licensed under the Common Public Attribution License version 1.0 (CPAL)

 EXHIBIT A. CPAL version 1.0
 �The contents of this file are subject to the CPAL version 1.0 (the �License�);
 you may not use this file except in compliance with the License. You may obtain a
 copy of the License at http://isa-tools.org/licenses/ISAcreator-license.html.
 The License is based on the Mozilla Public License version 1.1 but Sections
 14 and 15 have been added to cover use of software over a computer network and
 provide for limited attribution for the Original Developer. In addition, Exhibit
 A has been modified to be consistent with Exhibit B.

 Software distributed under the License is distributed on an �AS IS� basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 the specific language governing rights and limitations under the License.

 The Original Code is ISAcreator.
 The Original Developer is the Initial Developer. The Initial Developer of the
 Original Code is the ISA Team (Eamonn Maguire, eamonnmag@gmail.com;
 Philippe Rocca-Serra, proccaserra@gmail.com; Susanna-Assunta Sansone, sa.sanson@gmail.com;
 http://www.isa-tools.org). All portions of the code written by the ISA Team are
 Copyright (c) 2007-2011 ISA Team. All Rights Reserved.

 EXHIBIT B. Attribution Information
 Attribution Copyright Notice: Copyright (c) 2008-2011 ISA Team
 Attribution Phrase: Developed by the ISA Team
 Attribution URL: http://www.isa-tools.org
 Graphic Image provided in the Covered Code as file: http://isa-tools.org/licenses/icons/poweredByISAtools.png
 Display of Attribution Information is required in Larger Works which are defined in the CPAL as a work which combines Covered Code or portions thereof with code not governed by the terms of the CPAL.

 Sponsors:
 The ISA Team and the ISA software suite have been funded by the EU Carcinogenomics project (http://www.carcinogenomics.eu), the UK BBSRC (http://www.bbsrc.ac.uk), the UK NERC-NEBC (http://nebc.nerc.ac.uk) and in part by the EU NuGO consortium (http://www.nugo.org/everyone).
 */

package org.isatools.isacreator.io.importisa;

import com.sun.tools.javac.util.Pair;
import org.apache.commons.collections15.OrderedMap;
import org.isatools.isacreator.io.importisa.InvestigationFileProperties.InvestigationFileSection;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Publication;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 07/03/2011
 *         Time: 11:53
 */
public class InvestigationImportTest {

    @Test
    public void loadInvestigationFile() {
        File testInvestigationFile = new File("isatab files/BII-I-1/i_investigation.txt");

        System.out.println("__TESTING loadInvestigationFile() on " + testInvestigationFile.getName());

        InvestigationImport importer = new InvestigationImport();
        try {
            Pair<Boolean, OrderedMap<String, OrderedMap<InvestigationFileSection, OrderedMap<String, List<String>>>>> result = importer.importInvestigationFile(testInvestigationFile);

            assertTrue("Investigation did not validate\n" + printMessages(importer), result.fst);

            StructureToInvestigationMapper mapper = new StructureToInvestigationMapper();
            Investigation i = mapper.createInvestigationFromDataStructure(result.snd);

            System.out.println("Investigation title: " + i.getInvestigationTitle());
            System.out.println("Number of studies: " + i.getStudies().size());

            System.out.println("Getting investigation publications:");

            for (Publication publication : i.getPublications()) {
                System.out.println("Publication identifier: " + publication.getIdentifier());
                System.out.println("Publication title: " + publication.getPublicationTitle());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String printMessages(InvestigationImport importer) {

        StringBuffer toPrint = new StringBuffer();
        toPrint.append("\tProblems found in investigation: ");

        for (String message : importer.getMessages()) {
            toPrint.append("\t\t").append(message);
        }

        return toPrint.toString();
    }
}
